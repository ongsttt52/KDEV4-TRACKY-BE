package kernel360.trackyweb.statistic.infrastructure.repository.monthly;

import java.time.LocalDate;
import java.util.List;

import com.querydsl.core.Tuple;

public interface MonthlyStatisticDomainRepositoryCustom {

	List<Tuple> getMonthlyDataTuples(Long bizId, LocalDate currentDate, LocalDate targetDate);
}
