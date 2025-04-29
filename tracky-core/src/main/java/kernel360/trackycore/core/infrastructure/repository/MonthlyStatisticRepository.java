package kernel360.trackycore.core.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;

public interface MonthlyStatisticRepository extends JpaRepository<MonthlyStatisticEntity, Long> {
}
