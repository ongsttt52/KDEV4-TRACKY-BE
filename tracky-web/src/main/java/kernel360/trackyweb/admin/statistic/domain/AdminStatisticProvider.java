package kernel360.trackyweb.admin.statistic.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackyweb.admin.statistic.application.dto.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.AdminBizStatisticResponse;
import kernel360.trackyweb.admin.statistic.infrastructure.AdminStatisticRepositoryCustom;
import kernel360.trackyweb.statistic.domain.dto.MonthlyStat;
import kernel360.trackyweb.statistic.infrastructure.repository.MonthlyStatisticDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminStatisticProvider {

	private final AdminStatisticRepositoryCustom adminStatisticRepository;
	private final MonthlyStatisticDomainRepository monthlyStatisticDomainRepository;

	// 업체 목록 (업체명, 전체 차량 수, 운행중 차량 수, 오류율)
	public List<AdminBizListResponse> getAdminBizList(LocalDate selectedDate) {
		return adminStatisticRepository.fetchAdminBizList(selectedDate);
	}

	// 업체별 운행량 카드 섹션 + 운행량 그래프
	public AdminBizStatisticResponse getDriveStatByBizIdAndDate(Long bizId, LocalDate selectedDate) {

		AdminBizStatisticResponse response = adminStatisticRepository.getDriveStatByBizIdAndDate(bizId,
			selectedDate);
		List<MonthlyStat> monthlyStats = monthlyStatisticDomainRepository.getMonthlyStats(bizId, selectedDate,
			selectedDate.minusMonths(12));

		return response.update(monthlyStats);
	}
}
