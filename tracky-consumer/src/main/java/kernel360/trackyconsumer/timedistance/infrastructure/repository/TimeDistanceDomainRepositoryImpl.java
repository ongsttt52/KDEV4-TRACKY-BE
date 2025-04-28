package kernel360.trackyconsumer.timedistance.infrastructure.repository;

import static kernel360.trackycore.core.domain.entity.QTimeDistanceEntity.*;

import java.time.LocalDate;
import org.springframework.stereotype.Repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.TimeDistanceEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TimeDistanceDomainRepositoryImpl implements TimeDistanceRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public TimeDistanceEntity findByDateAndHourAndCar(LocalDate date, int hour, CarEntity car) {

		BooleanBuilder builder = new BooleanBuilder()
			.and(carEq(car))
			.and(dateEq(date))
			.and(hourEq(hour));

		return queryFactory
			.selectFrom(timeDistanceEntity)
			.where(builder)
			.fetchOne();
	}

	public BooleanExpression carEq(CarEntity car) {
		return timeDistanceEntity.car.eq(car);
	}
	
	public BooleanExpression dateEq(LocalDate date) {
		return timeDistanceEntity.date.eq(date);
	}
	
	public BooleanExpression hourEq(int hour) {
		return timeDistanceEntity.hour.eq(hour);
	}
}