package kernel360.trackyweb.car.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.DeviceEntity;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
}
