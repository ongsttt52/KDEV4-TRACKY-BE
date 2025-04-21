package kernel360.trackyweb.drive.infrastructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel360.trackycore.core.domain.entity.DriveEntity;

public interface DriveDomainRepositoryCustom {

	Page<DriveEntity> searchByFilter(String search, Pageable pageable);
}
