package kernel360.trackyweb.dashboard.infrastructure.repository;

import static kernel360.trackycore.core.domain.entity.QBizEntity.*;
import static kernel360.trackycore.core.domain.entity.QCarEntity.*;
import static kernel360.trackycore.core.domain.entity.QDriveEntity.*;
import static kernel360.trackycore.core.domain.entity.QGpsHistoryEntity.*;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DashGpsHistoryRepositoryCustomImpl implements DashGpsHistoryRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final HikariDataSource ds;

	/**
	 * 대시보드 차량 위치 지도 - 업체별 최신 GPS 조회
	 * @param bizUuid
	 * @return
	 */
	@Override
	public List<GpsHistoryEntity> getLatestGps(String bizUuid) {

		StopWatch st = new StopWatch();
		st.start("getLatestGps");
		List<GpsHistoryEntity> response = queryFactory
			.select(gpsHistoryEntity)
			.from(gpsHistoryEntity)
			.join(gpsHistoryEntity.drive, driveEntity)
			.where(
				// bizUuid 필터링 + MDN별 최신 drive.id 서브쿼리
				gpsHistoryEntity.drive.id.in(
					JPAExpressions
						.select(driveEntity.id.max())
						.from(driveEntity)
						.join(driveEntity.car, carEntity)
						.join(carEntity.biz, bizEntity)
						.where(bizEntity.bizUuid.eq(bizUuid))
						.groupBy(carEntity.mdn)
				)
			)
			.groupBy(driveEntity.id, driveEntity.car.mdn)
			.orderBy(gpsHistoryEntity.driveSeq.desc())
			.fetch();
		st.stop();

		log.info("{}", st.prettyPrint());
		return response;
	}
}