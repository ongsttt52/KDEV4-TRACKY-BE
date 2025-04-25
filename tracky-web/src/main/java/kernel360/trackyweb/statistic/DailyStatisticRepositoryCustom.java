package kernel360.trackyweb.statistic;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.domain.entity.DriveEntity;

@Repository
public interface DailyStatisticRepositoryCustom {

	public List<Long> getAllCarCount();

	public List<Long> getDailyDriveCarCount(LocalDateTime start, LocalDateTime end);

	public List<DriveEntity> getDailyDriveSec(Long biz, LocalDateTime start, LocalDateTime end);
}
