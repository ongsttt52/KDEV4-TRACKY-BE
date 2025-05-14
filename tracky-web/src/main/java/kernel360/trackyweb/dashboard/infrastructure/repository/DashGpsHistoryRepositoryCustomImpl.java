package kernel360.trackyweb.dashboard.infrastructure.repository;

import static kernel360.trackycore.core.domain.entity.QCarEntity.*;
import static kernel360.trackycore.core.domain.entity.QDriveEntity.*;
import static kernel360.trackycore.core.domain.entity.QGpsHistoryEntity.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DashGpsHistoryRepositoryCustomImpl implements DashGpsHistoryRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final HikariDataSource ds;

	/**
	 * 대시보드 차량 위치 지도 - 업체별 최신 GPS 조회
	 * @param bizId
	 * @return
	 */
	@Override
	public List<Tuple> getLatestGps(Long bizId) {

		return queryFactory
			.select(gpsHistoryEntity.lat, gpsHistoryEntity.lon)
			.from(gpsHistoryEntity)
			.join(gpsHistoryEntity.drive, driveEntity)
			.where(
				// bizUuid 필터링 + MDN별 최신 drive.id 서브쿼리
				gpsHistoryEntity.drive.id.in(
					JPAExpressions
						.select(driveEntity.id.max())
						.from(driveEntity)
						.join(driveEntity.car, carEntity)
						.where(carEntity.biz.id.eq(bizId))
						.groupBy(carEntity.mdn)
				)
			)
			.groupBy(driveEntity.id, driveEntity.car.mdn)
			.orderBy(gpsHistoryEntity.driveSeq.desc())
			.fetch();
	}
}