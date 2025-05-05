package kernel360.trackyweb.statistic.domain.dto;

public record MonthlyStat(
	int year,
	int month,
	int driveCount,
	double driveDistance
) {
	// public static List<MonthlyStat> from(List<Tuple> monthlyStatTuples) {
	// 	List<MonthlyStat> monthlyStats = monthlyStatTuples.stream().map(tuple -> {
	// 		int year = tuple.get(0, Integer.class);
	// 		int month = tuple.get(1, Integer.class);
	// 		int driveCount = tuple.get(2, Integer.class);
	// 		double driveDistance = tuple.get(3, Double.class);
	//
	// 		return new MonthlyStat(year, month, driveCount, driveDistance);
	// 	}).toList();
	//
	// 	return monthlyStats;
	// }
}
