package kernel360.trackyweb.dashboard.infrastructure.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackyweb.dashboard.domain.DashboardReader;
import kernel360.trackyweb.dashboard.domain.RentDashboardDto;
import kernel360.trackyweb.rent.infrastructure.repository.RentRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DashboardReaderImpl implementsl DashboardReader {

}