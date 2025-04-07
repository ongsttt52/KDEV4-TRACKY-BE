package kernel360.trackyconsumer.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import kernel360.trackyconsumer.application.dto.CycleGpsRequest;
import kernel360.trackyconsumer.infrastructure.repository.DriveRepository;
import kernel360.trackyconsumer.infrastructure.repository.GpsHistoryRepository;
import kernel360.trackyconsumer.presentation.dto.CycleInfoRequest;
import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarTestService {

    private final GpsHistoryRepository gpsHistoryRepository;
	private final DriveRepository driveRepository;
    
    @Async
    public void sendCycleInfo(CycleInfoRequest request) {
        
        // 처리 로직
        List<CycleGpsRequest> cycleGpsRequestList = request.getCList();

		DriveEntity drive = driveRepository.findByMdnAndOtime(request.getMdn(), request.getOTime());

		// GPS 쪼개서 정보 저장
		for (int i = 0; i < request.getCCnt(); i++) {
			saveCycleInfo(drive, request.getOTime(), cycleGpsRequestList.get(i).getSum(), cycleGpsRequestList.get(i));
		}
    }

    public void saveCycleInfo(DriveEntity drive, LocalDateTime oTime, double sum, CycleGpsRequest cycleGpsRequest) {
        // public void saveGpsMessage(LocalDateTime oTime, double sum, CycleGpsRequest cycleGpsRequest) {
    
            long maxSeq = gpsHistoryRepository.findMaxSeqByDrive(drive);
            GpsHistoryEntity gpsHistoryEntity = cycleGpsRequest.toGpsHistoryEntity(maxSeq + 1, drive, oTime, sum);
    
            gpsHistoryRepository.save(gpsHistoryEntity);
    
        }
}
