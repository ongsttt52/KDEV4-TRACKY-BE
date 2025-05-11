package kernel360.trackyweb.admin.statistic.application.dto.response;

public record AdminBizMonthlyResponse(
	int year,
	int month,
	int driveCount
) {
	// public static List<AdminBizMonthlyResponse> toDto(List<MonthlyStatisticEntity> monthlyStatisticEntities) {
	// 	if (monthlyStatisticEntities == null || monthlyStatisticEntities.isEmpty()) {
	// 		return Collections.emptyList();
	// 	}
	// 	YearMonth thisMonth = YearMonth.now();
	// 	YearMonth twelveMonthsAgo = thisMonth.minusMonths(11);
	//
	// 	Map<YearMonth, Integer> monthToDriveCount = new HashMap<>();
	// 	monthlyStatisticEntities.forEach(entity -> {
	// 		YearMonth yearMonth = YearMonth.from(entity.getDate());
	// 		monthToDriveCount.put(yearMonth, entity.getTotalDriveCount());
	// 	});
	//
	// 	return IntStream.range(0, 12)
	// 		.mapToObj(i -> {
	// 			YearMonth yearMonth = twelveMonthsAgo.plusMonths(i);
	// 			int count = monthToDriveCount.getOrDefault(yearMonth, 0);
	// 			return new AdminBizMonthlyResponse(yearMonth.atEndOfMonth(), count);
	// 		})
	// 		.collect(Collectors.toList());
	// }
}

