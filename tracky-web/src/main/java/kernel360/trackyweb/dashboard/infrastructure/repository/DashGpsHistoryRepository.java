package kernel360.trackyweb.dashboard.infrastructure.repository;

import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.infrastructure.repository.GpsHistoryRepository;

@Repository
public interface DashGpsHistoryRepository extends GpsHistoryRepository, DashGpsHistoryRepositoryCustom {

}
