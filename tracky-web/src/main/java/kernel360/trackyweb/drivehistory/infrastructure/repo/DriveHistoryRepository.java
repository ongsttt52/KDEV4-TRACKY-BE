package kernel360.trackyweb.drivehistory.infrastructure.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.domain.entity.DriveEntity;

public interface DriveHistoryRepository extends JpaRepository<DriveEntity, Long> {

	List<DriveEntity> findAllByCar_Mdn(String mdn);

	List<DriveEntity> findAllByRent_RentUuid(String rentUuid);
}
