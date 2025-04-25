package kernel360.trackyweb.statistic;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.QCarEntity;
import kernel360.trackycore.core.domain.entity.QDriveEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DailyStatisticRepositoryImpl implements DailyStatisticRepositoryCustom {
	private final JPAQueryFactory queryFactory;
	private final QDriveEntity drive = QDriveEntity.driveEntity;
	private final QCarEntity car = QCarEntity.carEntity;

	/**
	 * 업체별 전체 보유 차량
	 */
	@Override
	public List<Long> getAllCarCount() {
		return queryFactory
			.select(car.count())
			.from(car)
			.groupBy(car.biz.bizUuid)
			.fetch();
	}

	/**
	 * 업체별 일일 운행 차량
	 */
	@Override
	public List<Long> getDailyDriveCarCount(LocalDateTime start, LocalDateTime end) {
		return queryFactory
			.select(drive.car.mdn.countDistinct())
			.from(drive)
			.innerJoin(car)
			.on(drive.car.mdn.eq(car.mdn))
			.where(drive.driveOffTime.isNull()
				.or(drive.driveOffTime.goe(start).and(drive.driveOffTime.lt(end)))
			)
			.groupBy(car.biz.bizUuid)
			.fetch();
	}

	/**
	 * 업체별 일일 운행 시간
	 */
	@Override
	public List<DriveEntity> getDailyDriveSec(Long bizId, LocalDateTime start, LocalDateTime end) {
		return queryFactory
			.select(drive)
			.from(drive)
			.innerJoin(car)
			.on(drive.car.mdn.eq(car.mdn))
			.where(drive.driveOffTime.isNull()
				.or(drive.driveOffTime.goe(start).and(drive.driveOffTime.lt(end)))
			)
			.fetch();
	}

	public Long calculateDriveSec(List<DriveEntity> driveList) {
		long seconds = 0;
		for (DriveEntity driveEntity : driveList) {
			LocalDateTime onTime = driveEntity.getDriveOnTime();
			LocalDateTime offTime = driveEntity.getDriveOffTime();

			if (isToday(offTime)) {
				if (isToday(onTime)) {
					seconds += getDuration(onTime, offTime);
				} else {
					seconds += getDuration(offTime, LocalDate.now().atStartOfDay());
				}
			} else {
				if (isToday(onTime)) {
					seconds += getDuration(onTime, LocalDate.now().plusDays(1).atStartOfDay());
				} else {
					seconds += 24 * 60 * 60;
				}
			}
		}
		return seconds;
	}

	private boolean isToday(LocalDateTime dateTime) {
		return dateTime.toLocalDate().isEqual(LocalDate.now());
	}

	private long getDuration(LocalDateTime start, LocalDateTime end) {
		return Math.abs(Duration.between(start, end).getSeconds());
	}
}
