package kernel360.trackyweb.drive.domain.provider;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackyweb.drive.application.dto.request.DriveSearchFilterRequest;
import kernel360.trackyweb.drive.infrastructure.repository.DriveDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DriveDomainProvider {

	private final DriveDomainRepository driveDomainRepository;

	public Page<DriveEntity> searchByFilter(DriveSearchFilterRequest driveSearchFilterRequest) {
		return driveDomainRepository.searchByFilter(
			driveSearchFilterRequest.search(),
			driveSearchFilterRequest.pageable()
		);
	}
}
