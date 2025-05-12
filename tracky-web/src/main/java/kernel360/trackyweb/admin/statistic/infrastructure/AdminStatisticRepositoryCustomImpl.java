package kernel360.trackyweb.admin.statistic.infrastructure;

import static kernel360.trackycore.core.domain.entity.QBizEntity.*;
import static kernel360.trackycore.core.domain.entity.QCarEntity.*;
import static kernel360.trackycore.core.domain.entity.QDriveEntity.*;
import static kernel360.trackycore.core.domain.entity.QMonthlyStatisticEntity.*;
import static kernel360.trackycore.core.domain.entity.QTimeDistanceEntity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizMonthlyResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizStatisticResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.HourlyGraphResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminStatisticRepositoryCustomImpl implements AdminStatisticRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	/**
	 * 관리자 통계 - 업체 목록
	 * @return
	 */
	@Override
	public List<AdminBizListResponse> fetchAdminBizList() {
		LocalDateTime today = LocalDateTime.now();

		return queryFactory
			.select(Projections.constructor(
				AdminBizListResponse.class,
				bizEntity.bizName,
				carEntity.mdn.countDistinct().intValue(),
				driveEntity.car.mdn.countDistinct().intValue(),
				driveEntity.skipCount.sum().coalesce(0).intValue()
			))
			.from(bizEntity)
			.leftJoin(carEntity)
			.on(carEntity.biz.id.eq(bizEntity.id))
			.leftJoin(driveEntity)
			.on(driveEntity.car.mdn.eq(carEntity.mdn)
				.and(
					driveEntity.driveOffTime.isNull()
						.or(driveEntity.driveOffTime.eq(today))
				)
			)
			.groupBy(bizEntity.id)
			.fetch();
	}

	/**
	 * 관리자 통계 - 월별 운행량 카드
	 * @param bizId
	 * @param selectedDate
	 * @return
	 */
	@Override
	public AdminBizStatisticResponse getDriveStatByBizIdAndDate(Long bizId, LocalDate selectedDate) {
		LocalDate startOfMonth = selectedDate.withDayOfMonth(1);
		LocalDate endOfMonth = selectedDate.with(TemporalAdjusters.lastDayOfMonth());

		JPAQuery<AdminBizStatisticResponse> query =
			queryFactory.select(Projections.constructor(
					AdminBizStatisticResponse.class,
					monthlyStatisticEntity.totalDriveCount.coalesce(0).sum(),
					monthlyStatisticEntity.totalDriveDistance.coalesce(0.0).sum(),
					monthlyStatisticEntity.totalDriveSec.coalesce(0L).sum().intValue()
				))
				.from(monthlyStatisticEntity)
				.where(
					(bizId != null
						? monthlyStatisticEntity.biz.id.eq(bizId)
						: Expressions.TRUE)
						.and(monthlyStatisticEntity.date.between(startOfMonth, endOfMonth))
				);
		if (bizId != null) {
			query.groupBy(monthlyStatisticEntity.biz.id);
		}
		return query.fetchOne();
	}

	/**
	 * 관리자 통계 - 월별 운행량 그래프
	 * @param bizId
	 * @return
	 */
	@Override
	public List<AdminBizMonthlyResponse> getTotalDriveCountInOneYear(Long bizId, LocalDate selectedDate) {
		LocalDate endDate = selectedDate.with(TemporalAdjusters.lastDayOfMonth()); // selectedDate를 월말로 설정
		LocalDate startDate = YearMonth.now().minusMonths(12).atEndOfMonth();

		return queryFactory
			.select(Projections.constructor(
				AdminBizMonthlyResponse.class,
				monthlyStatisticEntity.date.year(),
				monthlyStatisticEntity.date.month(),
				monthlyStatisticEntity.totalDriveCount.sum().coalesce(0)
			))
			.from(monthlyStatisticEntity)
			// bizId가 null일 경우 전체 검색
			.where(
				(bizId != null
					? monthlyStatisticEntity.biz.id.eq(bizId)
					: Expressions.TRUE)
					.and(monthlyStatisticEntity.date.between(startDate, endDate))
			)
			.groupBy(monthlyStatisticEntity.date.yearMonth())
			.orderBy(monthlyStatisticEntity.date.yearMonth().asc())
			.fetch();
	}

	/**
	 * 관리자 통계 - 시간별 운행량 그래프
	 * @param bizId
	 * @param selectedDate
	 * @return
	 */
	@Override
	public List<HourlyGraphResponse> getHourlyDriveCounts(Long bizId, LocalDate selectedDate) {

		return queryFactory
			.select(Projections.constructor(
				HourlyGraphResponse.class,
				timeDistanceEntity.hour,
				timeDistanceEntity.count()
			))
			.from(timeDistanceEntity)
			.where(
				(bizId != null
					? timeDistanceEntity.biz.id.eq(bizId)
					: Expressions.TRUE)
					.and(timeDistanceEntity.date.eq(selectedDate))
			)
			.groupBy(timeDistanceEntity.hour)
			.fetch();
	}
}