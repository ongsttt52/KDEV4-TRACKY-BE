package kernel360.trackyweb.drive.infrastructure.repository;

import static kernel360.trackycore.core.domain.entity.QDriveEntity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DriveDomainRepositoryImpl implements DriveDomainRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<DriveEntity> searchByFilter(String mdn, LocalDateTime startDateTime, LocalDateTime endDateTime,
		Pageable pageable) {
		// 시작~종료 시간이 지정되지 않을 시 최신 20개 데이터 조회
		boolean isUnbounded = isUnboundedSearch(startDateTime, endDateTime);

		BooleanBuilder condition = buildCondition(mdn, startDateTime, endDateTime, isUnbounded);

		JPAQuery<DriveEntity> query = queryFactory
			.selectFrom(driveEntity)
			.where(condition)
			.orderBy(driveEntity.driveOnTime.desc());

		List<DriveEntity> content = isUnbounded
			? query.offset(0).limit(20).fetch()
			: query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

		long total = isUnbounded
			? content.size()
			: fetchTotalCount(condition);

		Pageable effectivePageable = isUnbounded
			? PageRequest.of(0, 20)
			: pageable;

		return new PageImpl<>(content, effectivePageable, total);
	}

	private boolean isUnboundedSearch(LocalDateTime start, LocalDateTime end) {
		return start == null && end == null;
	}

	private BooleanBuilder buildCondition(String mdn, LocalDateTime start, LocalDateTime end, boolean isUnbounded) {
		BooleanBuilder builder = new BooleanBuilder();

		if (StringUtils.isNotBlank(mdn)) {
			builder.and(driveEntity.car.mdn.eq(mdn));
		}
		if (!isUnbounded) {
			if (start != null)
				builder.and(driveEntity.driveOnTime.goe(start));
			if (end != null)
				builder.and(driveEntity.driveOffTime.loe(end));
		}

		return builder;
	}

	private long fetchTotalCount(BooleanBuilder condition) {
		return Optional.ofNullable(queryFactory
			.select(driveEntity.count())
			.from(driveEntity)
			.where(condition)
			.fetchOne()
		).orElse(0L);
	}

}
