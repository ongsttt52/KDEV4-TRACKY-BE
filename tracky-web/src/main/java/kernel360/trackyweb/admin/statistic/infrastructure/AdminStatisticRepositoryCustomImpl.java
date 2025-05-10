package kernel360.trackyweb.admin.statistic.infrastructure;

import static kernel360.trackycore.core.domain.entity.QBizEntity.*;
import static kernel360.trackycore.core.domain.entity.QCarEntity.*;
import static kernel360.trackycore.core.domain.entity.QDriveEntity.*;
import static kernel360.trackycore.core.domain.entity.QTimeDistanceEntity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackyweb.admin.statistic.application.dto.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.AdminBizStatisticResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminStatisticRepositoryCustomImpl implements AdminStatisticRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	/**
	 * 관리자 통계 - 업체 목록
	 * @param selectedDate
	 * @return
	 */
	@Override
	public List<AdminBizListResponse> fetchAdminBizList(LocalDate selectedDate) {
		LocalDateTime start = selectedDate.atStartOfDay();
		LocalDateTime end = selectedDate.plusDays(1).atStartOfDay();

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
						.or(driveEntity.driveOffTime.between(start, end))
				)
			)
			.groupBy(bizEntity.id)
			.fetch();
	}

	/**
	 * 관리자 통계 - 업체별 운행량 카드 섹션
	 * @param bizId
	 * @param selectedDate
	 * @return
	 */
	@Override
	public AdminBizStatisticResponse getDriveStatByBizIdAndDate(Long bizId, LocalDate selectedDate) {

		return queryFactory
			.select(Projections.constructor(
				AdminBizStatisticResponse.class,
				driveEntity.id.countDistinct().intValue(),
				timeDistanceEntity.distance.sum(),
				timeDistanceEntity.seconds.sum(),
				Expressions.nullExpression(List.class)
			))
			.from(timeDistanceEntity)
			.join(driveEntity)
			.on(driveEntity.car.mdn.eq(timeDistanceEntity.car.mdn))
			.where(isEqualsBizId(bizId)
				.and(timeDistanceEntity.date.eq(selectedDate))
			)
			.groupBy(timeDistanceEntity.biz.id)
			.fetchOne();
	}

	private BooleanExpression isEqualsBizId(Long bizId) {
		if (bizId == null) {
			return Expressions.TRUE;
		}
		return timeDistanceEntity.biz.id.eq(bizId);
	}
}
