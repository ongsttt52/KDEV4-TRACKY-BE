package kernel360.trackycore.core.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.common.entity.BizEntity;

public interface BizRepository extends JpaRepository<BizEntity, Long> {
}
