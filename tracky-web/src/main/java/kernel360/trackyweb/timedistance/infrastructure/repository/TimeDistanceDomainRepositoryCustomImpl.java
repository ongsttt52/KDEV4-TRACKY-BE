package kernel360.trackyweb.timedistance.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackyweb.timedistance.application.dto.internal.OperationTime;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TimeDistanceDomainRepositoryCustomImpl implements TimeDistanceDomainRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<OperationTime> getTotalOperationTimeGroupedByBIzId(LocalDate targetDate) {

		return null;
	}

}
