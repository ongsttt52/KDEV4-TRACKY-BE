package kernel360.trackyconsumer.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.infrastructure.repository.DriveRepository;

public interface DriveDomainRepository extends DriveRepository, DriveRepositoryCustom {
}
