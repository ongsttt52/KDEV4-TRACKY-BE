package kernel360.trackyweb.drive.domain.provider;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import kernel360.trackyweb.drive.application.dto.internal.NonOperatedCar;
import kernel360.trackyweb.drive.application.dto.internal.OperationTotalCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackyweb.drive.application.dto.internal.OperationCarCount;
import kernel360.trackyweb.drive.domain.DriveHistory;
import kernel360.trackyweb.drive.infrastructure.repository.DriveDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DriveDomainProvider {

    private final DriveDomainRepository driveDomainRepository;

    public Page<DriveEntity> searchDrivesByFilter(
            String search,
            String mdn,
            LocalDate startDateTime,
            LocalDate endDateTime,
            Pageable pageable) {
        return driveDomainRepository.searchByFilter(search, mdn, startDateTime, endDateTime, pageable);
    }

    public Page<DriveEntity> findRunningDriveList(
            String search,
            Pageable pageable
    ) {
        return driveDomainRepository.findRunningDriveList(search, pageable);
    }

    public DriveEntity findRunningDriveById(Long driveId) {
        return driveDomainRepository.findRunningDriveById(driveId)
                .orElseThrow(() -> GlobalException.throwError(ErrorCode.NOT_REALTIME_DRIVE));
    }

    public DriveHistory findByDriveId(Long driveId) {
        return driveDomainRepository.findByDriveId(driveId)
                .orElseThrow(() -> GlobalException.throwError(ErrorCode.DRIVE_NOT_FOUND));
    }

    public List<DriveEntity> findByMdn(String mdn) {
        return driveDomainRepository.findDriveListByMdn(mdn);
    }

    public Double getTotalDriveDistance() {
        return driveDomainRepository.getTotalDriveDistance();
    }

    public Long getTotalDriveDurationInMinutes() {
        return driveDomainRepository.getTotalDriveDurationInMinutes();
    }

    //일일 통계 - 당일 운행 차량 수 (distinct mdn)
    public Map<Long, Integer> countDailyOperationCar(LocalDate targetDate) {
        return OperationCarCount.toMap(driveDomainRepository.getDailyOperationCar(targetDate));
    }

    //일일 통계 - 당일 총 운행 수
    public Map<Long, Integer> countDailyTotalOperation(LocalDate targetDate) {
        return OperationTotalCount.toMap(driveDomainRepository.getDailyTotalOperation(targetDate));
    }

    //월별 통계 - 미운행 차량 수
    public Map<Long, Integer> getNonOperatedCars(LocalDate targetDate) {
        return NonOperatedCar.toMap(driveDomainRepository.getNonOperatedCars(targetDate));
    }
}
