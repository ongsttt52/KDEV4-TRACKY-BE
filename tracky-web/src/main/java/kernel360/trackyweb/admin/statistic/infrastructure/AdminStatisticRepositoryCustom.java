package kernel360.trackyweb.admin.statistic.infrastructure;

import java.time.LocalDate;
import java.util.List;

import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizMonthlyResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizStatisticResponse;

public interface AdminStatisticRepositoryCustom {

	List<AdminBizListResponse> fetchAdminBizList();

	AdminBizStatisticResponse getDriveStatByBizIdAndDate(Long bizId, LocalDate selectedDate);

	List<AdminBizMonthlyResponse> getTotalDriveCountInOneYear(Long bizId, LocalDate selectedDate);
}
