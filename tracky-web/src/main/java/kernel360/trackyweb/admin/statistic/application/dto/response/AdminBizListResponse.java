package kernel360.trackyweb.admin.statistic.application.dto.response;

import java.util.List;

public record AdminBizListResponse(
	String bizName,
	int totalCarCount,
	int drivingCarCount,
	int skipCount
) {
	public static List<AdminBizListResponse> addSum(List<AdminBizListResponse> list) {
		int totalCarSum = 0;
		int drivingCarSum = 0;
		int skipCountSum = 0;

		for (int i = 0; i < list.size(); i++) {
			AdminBizListResponse response = list.get(i);
			totalCarSum += response.totalCarCount;
			drivingCarSum += response.drivingCarCount;
			skipCountSum += response.skipCount;
		}

		list.add(0, new AdminBizListResponse("전체", totalCarSum, drivingCarSum, skipCountSum));

		return list;
	}
}