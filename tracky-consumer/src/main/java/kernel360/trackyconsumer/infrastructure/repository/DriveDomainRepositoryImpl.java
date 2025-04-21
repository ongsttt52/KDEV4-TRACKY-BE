package kernel360.trackyconsumer.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.QDriveEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DriveDomainRepositoryImpl implements DriveRepositoryCustom {
	private final JPAQueryFactory queryFactory;
	private final QDriveEntity driveEntity = QDriveEntity.driveEntity;

	@Override
	public Optional<DriveEntity> getDrive(CarEntity car, LocalDateTime onTime) {
		DriveEntity result = queryFactory
			.selectFrom(driveEntity)
			.where(driveEntity.car.eq(car)
				.and(driveEntity.driveOnTime.loe(onTime)))
			.orderBy(driveEntity.id.desc())
			.fetchFirst();

		return Optional.ofNullable(result);
	}
}
