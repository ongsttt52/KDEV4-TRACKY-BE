package kernel360.trackyconsumer.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.QRentEntity;
import kernel360.trackycore.core.domain.entity.RentEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RentDomainRepositoryImpl implements RentRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final QRentEntity rentEntity = QRentEntity.rentEntity;

	@Override
	public Optional<RentEntity> getRent(CarEntity car, LocalDateTime carOnTime) {
		RentEntity result = queryFactory
			.selectFrom(rentEntity)
			.where(rentEntity.car.eq(car)
				.and(rentEntity.rentStime.loe(carOnTime))
				.and(rentEntity.rentEtime.goe(carOnTime))
			)
			.orderBy(rentEntity.rentUuid.desc())
			.fetchFirst();

		return Optional.ofNullable(result);
	}
}
