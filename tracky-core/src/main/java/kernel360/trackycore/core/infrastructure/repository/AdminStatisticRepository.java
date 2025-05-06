package kernel360.trackycore.core.infrastructure.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.domain.entity.AdminStatisticEntity;

public interface AdminStatisticRepository extends JpaRepository<AdminStatisticEntity, Long> {

	AdminStatisticEntity findByDate(LocalDate now);
}
