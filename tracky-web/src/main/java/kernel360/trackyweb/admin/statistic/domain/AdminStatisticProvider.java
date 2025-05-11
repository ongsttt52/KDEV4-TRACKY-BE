package kernel360.trackyweb.admin.statistic.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackyweb.admin.statistic.application.dto.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.AdminBizStatisticResponse;
import kernel360.trackyweb.admin.statistic.infrastructure.AdminStatisticRepositoryCustom;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminStatisticProvider {

	private final AdminStatisticRepositoryCustom adminStatisticRepository;

	public List<AdminBizListResponse> getAdminBizList() {
		return adminStatisticRepository.fetchAdminBizList();
	}

	public AdminBizStatisticResponse getDriveStatByBizIdAndDate(Long bizId, LocalDate selectedDate) {

		AdminBizStatisticResponse response = adminStatisticRepository.getDriveStatByBizIdAndDate(bizId,
			selectedDate);

		if (response == null)
			return new AdminBizStatisticResponse(0, 0, 0);
		else
			return response;
	}
}
