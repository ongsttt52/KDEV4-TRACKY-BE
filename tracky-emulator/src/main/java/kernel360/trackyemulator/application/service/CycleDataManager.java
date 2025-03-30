package kernel360.trackyemulator.application.service;

import kernel360.trackyemulator.application.service.client.CycleRequestClient;
import kernel360.trackyemulator.application.service.util.RandomLocationGenerator;
import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.presentation.dto.CycleGpsRequest;
import kernel360.trackyemulator.presentation.dto.CycleInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class CycleDataManager {

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
    private final CycleRequestClient cycleRequestClient;
    private final RandomLocationGenerator locationGenerator;

    private final Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();

    public void startSending(EmulatorInstance instance) {
        Runnable task = () -> handlePeriodicData(instance);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        taskMap.put(instance.getMdn(), future);
    }

    public void stopSending(String mdn) {
        Optional.ofNullable(taskMap.remove(mdn)).ifPresent(future -> future.cancel(true));
        log.info("{} → 주기 데이터 스케줄 종료", mdn);
    }

    private void handlePeriodicData(EmulatorInstance instance) {
        CycleGpsRequest data = locationGenerator.generateNextCycleData(instance);
        instance.addCycleData(data);
        log.info("{} → 1초 데이터 생성 완료 (버퍼: {}/{})", instance.getMdn(), instance.getCycleBuffer().size(), 60);

        if (instance.isBufferFull()) {
            CycleInfoRequest cycleInfoRequest = buildCycleInfoRequest(instance);
            periodicRequestClient.sendPeriodicData(cycleInfoRequest);
            log.info("{} → 60초 데이터 전송 완료", instance.getMdn());
            instance.clearBuffer();
        }
    }

    private CycleInfoRequest buildCycleInfoRequest(EmulatorInstance instance) {
        List<CycleGpsRequest> buffer = instance.clearBuffer();
        return CycleInfoRequest.of(

                )

    }
}