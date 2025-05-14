package kernel360.trackyweb.dashboard.infrastructure.repository;

import java.util.List;

import com.querydsl.core.Tuple;

public interface DashGpsHistoryRepositoryCustom {
	List<Tuple> getLatestGps(Long bizId);
}
