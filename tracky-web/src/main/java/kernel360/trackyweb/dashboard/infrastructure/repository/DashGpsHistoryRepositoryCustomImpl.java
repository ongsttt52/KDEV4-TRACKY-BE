package kernel360.trackyweb.dashboard.infrastructure.repository;

import static kernel360.trackycore.core.domain.entity.QDriveEntity.*;
import static kernel360.trackycore.core.domain.entity.QGpsHistoryEntity.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DashGpsHistoryRepositoryCustomImpl implements DashGpsHistoryRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<GpsHistoryEntity> getLatestGps(String bizUuid) {
		// 1. 각 MDN별 최신 GPS ID 조회
		List<Tuple> latestGpsInfo = queryFactory
			.select(driveEntity.car.mdn, gpsHistoryEntity.driveSeq.max())
			.from(gpsHistoryEntity)
			.join(driveEntity).on(gpsHistoryEntity.drive.id.eq(driveEntity.id))
			.where(driveEntity.car.biz.bizUuid.eq(bizUuid))
			.groupBy(driveEntity.car.mdn)
			.fetch();

		// 2. 최신 GPS ID들로 실제 엔티티 조회
		List<UUID> latestGpsIds = latestGpsInfo.stream()
			.map(tuple -> tuple.get(gpsHistoryEntity.driveSeq.max()))
			.toList();

		if (latestGpsIds.isEmpty()) {
			return Collections.emptyList();
		}

		return queryFactory
			.selectFrom(gpsHistoryEntity)
			.join(driveEntity).on(gpsHistoryEntity.drive.id.eq(driveEntity.id))
			.where(
				gpsHistoryEntity.driveSeq.in(latestGpsIds),
				driveEntity.car.biz.bizUuid.eq(bizUuid)
			)
			.fetch();
	}
}
