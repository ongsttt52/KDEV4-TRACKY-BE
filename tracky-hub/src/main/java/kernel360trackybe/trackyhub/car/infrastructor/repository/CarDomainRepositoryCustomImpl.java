package kernel360trackybe.trackyhub.car.infrastructor.repository;

import static kernel360.trackycore.core.domain.entity.QCarEntity.*;
import static kernel360.trackycore.core.domain.entity.QRentEntity.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.CarEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CarDomainRepositoryCustomImpl implements CarDomainRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<CarEntity> availableRent() {
		LocalDateTime now = LocalDateTime.now();

		return queryFactory
			.select(carEntity)
			.from(carEntity)
			.join(rentEntity).on(
				rentEntity.car.eq(carEntity),
				rentEntity.rentEtime.goe(now)
			)
			.fetch();
	}
}


