package kernel360.trackyweb.admin.statistic.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.BizEntity;
import kernel360.trackycore.core.infrastructure.repository.BizRepository;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizMonthlyResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizStatisticResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.HourlyGraphResponse;
import kernel360.trackyweb.admin.statistic.infrastructure.AdminStatisticRepositoryCustom;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminStatisticProvider {

	private final AdminStatisticRepositoryCustom adminStatisticRepository;

	private final BizRepository bizRepository;

	public List<AdminBizListResponse> getAdminBizList() {
		return adminStatisticRepository.fetchAdminBizList();
	}

	public AdminBizStatisticResponse getDriveStatByBizIdAndDate(String bizName, LocalDate selectedDate) {

		AdminBizStatisticResponse response = adminStatisticRepository.getDriveStatByBizIdAndDate(
			mapToBizId(bizName), selectedDate
		);

		if (response == null) {
			return new AdminBizStatisticResponse(0, 0, 0);
		}
		return response;
	}

	public List<AdminBizMonthlyResponse> getAdminBizMonthlyDriveCount(String bizName, LocalDate selectedDate) {

		List<AdminBizMonthlyResponse> response = adminStatisticRepository.getTotalDriveCountInOneYear(
			mapToBizId(bizName), selectedDate
		);

		if (response == null) {
			return List.of();
		}
		return response;
	}

	public List<HourlyGraphResponse> getAdminBizHourlyDriveCount(String bizName, LocalDate selectedDate) {

		List<HourlyGraphResponse> response = adminStatisticRepository.getHourlyDriveCounts(
			mapToBizId(bizName), selectedDate
		);

		if (response == null) {
			return List.of();
		}
		return response;
	}

	private Long mapToBizId(String bizName) {

		if (bizName.isBlank()) {
			return null;
		}
		BizEntity biz = bizRepository.findByBizName(bizName)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.BIZ_NOT_FOUND));

		return biz.getId();
	}
}
