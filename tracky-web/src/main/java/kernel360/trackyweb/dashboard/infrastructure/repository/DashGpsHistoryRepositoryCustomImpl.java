package kernel360.trackyweb.dashboard.infrastructure.repository;

import static kernel360.trackycore.core.domain.entity.QDriveEntity.*;
import static kernel360.trackycore.core.domain.entity.QGpsHistoryEntity.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackycore.core.domain.entity.QDriveEntity;
import kernel360.trackycore.core.domain.entity.QGpsHistoryEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DashGpsHistoryRepositoryCustomImpl implements DashGpsHistoryRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<GpsHistoryEntity> getLatestGps(String bizUuid) {
		QGpsHistoryEntity gSub = new QGpsHistoryEntity("gSub");
		QDriveEntity dSub = new QDriveEntity("dSub");

		return queryFactory
			.selectFrom(gpsHistoryEntity)
			.join(gpsHistoryEntity.drive, driveEntity).fetchJoin()
			.where(
				Expressions.list(
					driveEntity.car.mdn,
					gpsHistoryEntity.oTime
				).in(
					JPAExpressions
						.select(
							dSub.car.mdn,
							gSub.oTime.max()
						)
						.from(gSub)
						.join(gSub.drive, dSub)
						.groupBy(dSub.car.mdn)
				)
			)
			.fetch();
	}
}
