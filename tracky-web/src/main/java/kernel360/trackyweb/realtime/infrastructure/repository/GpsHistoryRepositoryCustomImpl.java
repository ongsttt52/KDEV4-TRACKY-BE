package kernel360.trackyweb.realtime.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackycore.core.domain.entity.QGpsHistoryEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GpsHistoryRepositoryCustomImpl implements GpsHistoryRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<GpsHistoryEntity> findOneGpsByDriveId(Long id) {
		QGpsHistoryEntity gps = QGpsHistoryEntity.gpsHistoryEntity;

		GpsHistoryEntity result = queryFactory
			.selectFrom(gps)
			.where(gps.drive.id.eq(id))
			.orderBy(gps.oTime.desc())
			.limit(1)
			.fetchFirst();

		return Optional.ofNullable(result);
	}

	@Override
	public List<GpsHistoryEntity> findGpsPathBeforeTime(Long driveId, LocalDateTime nowTime) {
		QGpsHistoryEntity gps = QGpsHistoryEntity.gpsHistoryEntity;

		return queryFactory
			.selectFrom(gps)
			.where(
				gps.drive.id.eq(driveId),
				gps.oTime.loe(nowTime)
			)
			.orderBy(gps.oTime.asc())
			.fetch();
	}

	@Override
	public List<GpsHistoryEntity> findGpsPathAfterTime(Long driveId, LocalDateTime nowTime) {
		QGpsHistoryEntity gps = QGpsHistoryEntity.gpsHistoryEntity;

		return queryFactory
			.selectFrom(gps)
			.where(
				gps.drive.id.eq(driveId),
				gps.oTime.loe(nowTime)
			)
			.orderBy(gps.oTime.asc())
			.fetch();
	}
}
