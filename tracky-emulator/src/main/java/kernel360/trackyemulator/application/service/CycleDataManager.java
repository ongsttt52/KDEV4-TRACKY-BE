package kernel360.trackyemulator.application.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import kernel360.trackyemulator.application.service.client.ControlClient;
import kernel360.trackyemulator.application.service.dto.request.CycleGpsRequest;
import kernel360.trackyemulator.application.service.generator.CycleGpsDataGenerator;
import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.presentation.view.dto.CycleLogResponse;
import kernel360.trackyemulator.presentation.view.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CycleDataManager {

	private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
	private final ControlClient controlClient;
	private final CycleGpsDataGenerator gpsDataGenerator;
	private final SseService sseService;
	private final Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();

	/**
	 * 주기 데이터 생성 스케줄 시작
	 */
	public void startSending(EmulatorInstance instance) {
		Runnable task = () -> handlePeriodicData(instance);
		ScheduledFuture<?> future = executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
		taskMap.put(instance.getMdn(), future);
	}

	/**
	 * 주기 데이터 생성 스케줄 종료 + 남은 데이터 전송
	 */
	public void stopSending(EmulatorInstance instance) {
		String mdn = instance.getMdn();

		Optional.ofNullable(taskMap.remove(mdn)).ifPresent(future -> {
			future.cancel(true);
			log.info("{} → 주기 데이터 스케줄 종료", mdn);
		});

		if (!instance.getCycleBuffer().isEmpty()) {
			controlClient.sendCycleData(instance);
			log.info("{} → 시동 OFF로 인한 잔여 데이터 전송 완료 (버퍼 크기: {})", mdn, instance.getCycleBuffer().size());
			instance.clearBuffer();
		}
	}

	/**
	 * 1초마다 실행되는 주기 데이터 처리
	 */
	private void handlePeriodicData(EmulatorInstance instance) {
		CycleGpsRequest data = gpsDataGenerator.generate(instance);
		instance.addCycleData(data);
		log.info("{} → 1초 데이터 생성 완료 (버퍼: {}/{}), gps info : {}",
			instance.getMdn(),
			instance.getCycleBuffer().size(),
			60,
			data.gpsInfo()
		);

		if (instance.isBufferFull()) {
			controlClient.sendCycleData(instance);
			log.info("{} → 60초 데이터 전송 완료", instance.getMdn());
			log.info("60초 주기 데이터의 gps info cycle : {}", instance.getCycleBuffer());
			instance.clearBuffer();

			// 뷰에 내려줄 정보
			CycleLogResponse response = new CycleLogResponse(
				instance.getMdn(),
				instance.getCycleLastGpsInfo().getLat() / 1e6,
				instance.getCycleLastGpsInfo().getLon() / 1e6,
				instance.getCycleLastGpsInfo().getSpd()
			);
			sseService.sendMessage(response);
		}
	}
}
