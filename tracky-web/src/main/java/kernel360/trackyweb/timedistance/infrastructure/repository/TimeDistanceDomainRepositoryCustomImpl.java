package kernel360.trackyweb.timedistance.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.QTimeDistanceEntity;
import kernel360.trackyweb.timedistance.application.dto.internal.OperationTime;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TimeDistanceDomainRepositoryCustomImpl implements TimeDistanceDomainRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Tuple> countByBizIdAndDateGroupedByHour(Long bizId, LocalDate targetDate) {
		QTimeDistanceEntity e = QTimeDistanceEntity.timeDistanceEntity;

		return queryFactory.select(e.hour, e.count())
			.from(e)
			.where(e.biz.id.eq(bizId)
				.and(e.date.eq(targetDate))
			)
			.groupBy(e.hour)
			.orderBy(e.hour.asc())
			.fetch();
	}

	@Override
	public List<OperationTime> getTotalOperationTimeGroupedByBIzId(LocalDate targetDate) {

		return null;
	}

}
