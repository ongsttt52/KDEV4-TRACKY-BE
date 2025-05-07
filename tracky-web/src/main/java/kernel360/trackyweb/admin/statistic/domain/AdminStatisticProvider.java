package kernel360.trackyweb.admin.statistic.domain;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.AdminStatisticEntity;
import kernel360.trackycore.core.infrastructure.repository.AdminStatisticRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminStatisticProvider {

	private final AdminStatisticRepository adminStatisticRepository;

	public AdminStatisticEntity getAdminStatistic(LocalDate date) {

		return adminStatisticRepository.findByDate(date);
	}
}
