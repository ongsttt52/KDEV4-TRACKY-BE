package kernel360.trackyweb.timedistance.infrastructure.repository;

import static kernel360.trackycore.core.domain.entity.QTimeDistanceEntity.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackyweb.timedistance.application.dto.internal.OperationTime;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TimeDistanceDomainRepositoryCustomImpl implements TimeDistanceDomainRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<OperationTime> getTotalOperationTimeGroupedByBIzId(LocalDate date) {
		return List.of();
	}

	@Override
	public List<Tuple> countByBizIdAndDateGroupedByHour(Long bizId, LocalDate date) {

		return queryFactory.select(timeDistanceEntity.hour, timeDistanceEntity.count())
			.from(timeDistanceEntity)
			.where(timeDistanceEntity.biz.id.eq(bizId)
				.and(timeDistanceEntity.date.eq(date))
			)
			.groupBy(timeDistanceEntity.hour)
			.orderBy(timeDistanceEntity.hour.asc())
			.fetch();
	}
}