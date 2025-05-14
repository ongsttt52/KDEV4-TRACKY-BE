package kernel360.trackyweb.statistic.domain.provider;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminGraphStatsResponse;
import kernel360.trackyweb.statistic.application.dto.response.MonthlyStatisticResponse;
import kernel360.trackyweb.statistic.infrastructure.repository.monthly.MonthlyStatisticDomainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MonthlyStatisticProvider {

	private final MonthlyStatisticDomainRepository monthlyStatisticRepository;

	public MonthlyStatisticEntity getMonthlyStatistic(Long bizId, LocalDate date) {

		return monthlyStatisticRepository.findByBizIdAndDate(bizId, date);
	}

	@Transactional
	public void saveMonthlyStatistic(List<MonthlyStatisticEntity> resultEntities) {

		for (MonthlyStatisticEntity entity : resultEntities) {
			MonthlyStatisticEntity existEntity = monthlyStatisticRepository.findByBizIdAndDate(
				entity.getBizId(), entity.getDate());

			if (existEntity != null) {
				existEntity.update(entity.getDate(), entity.getTotalCarCount(), entity.getNonOperatingCarCount(),
					entity.getAvgOperationRate(), entity.getTotalDriveSec(), entity.getTotalDriveCount(),
					entity.getTotalDriveDistance());
			} else {
				monthlyStatisticRepository.save(entity);
			}

			log.info("기존 date: {}", existEntity.getDate());
			log.info("새 date: {}", entity.getDate());
		}
	}

	public List<MonthlyStatisticResponse.MonthlyStats> getMonthlyStats(Long bizId, LocalDate currentDate,
		LocalDate targetDate) {

		return monthlyStatisticRepository.getMonthlyStats(bizId, currentDate, targetDate);
	}

	public MonthlyStatisticEntity getDashBoardStatistic(String bizUuid) {
		return monthlyStatisticRepository.findLatestMonthlyStatistic(bizUuid);
	}

	public List<AdminGraphStatsResponse.NonOperatedCar> getNonOperatedCarWithBizName() {
		return monthlyStatisticRepository.getNonOperatedCarWithBizName();
	}

}
