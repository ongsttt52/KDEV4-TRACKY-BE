package kernel360.trackyweb.drive.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.QDriveEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DriveDomainRepositoryImpl implements DriveDomainRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final QDriveEntity drive = QDriveEntity.driveEntity;

	@Override
	public Page<DriveEntity> searchByFilter(String search, Pageable pageable) {

		BooleanBuilder builder = searchByFilterBuilder(search, drive);

		JPAQuery<DriveEntity> query = queryFactory
			.selectFrom(drive)
			.where(builder);

		// 검색어가 있을 때 정렬 로직 추가
		if (search != null && !search.isBlank()) {
			// 포함 여부: carPlate에 검색어 포함 → 우선순위로 사용
			NumberTemplate<Integer> carPlatePriority = Expressions.numberTemplate(
				Integer.class, "CASE WHEN {1} LIKE CONCAT('%', {0}, '%') THEN 0 ELSE 1 END",
				search, drive.car.carPlate
			);

			// 포함 위치: 포함된 경우, 앞에 있을수록 우선
			NumberTemplate<Integer> carPlatePos = Expressions.numberTemplate(
				Integer.class, "LOCATE({0}, {1})", search, drive.car.carPlate
			);

			query.orderBy(
				carPlatePriority.asc(),   // 0 (carPlate 포함) 먼저
				carPlatePos.asc(),        // 앞쪽 위치 먼저
				drive.car.carPlate.asc()        // 그 다음 carPlate 순서
			);
		} else {
			query.orderBy(drive.car.carPlate.asc()); // 검색어 없으면 단순 carPlate 정렬
		}

		List<DriveEntity> content = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = Optional.ofNullable(
			queryFactory
				.select(drive.count())
				.from(drive)
				.where(builder)
				.fetchOne()
		).orElse(0L);

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanBuilder searchByFilterBuilder(String search, QDriveEntity drive) {
		BooleanBuilder builder = new BooleanBuilder();

		if (search != null && !search.isBlank()) {
			builder.and(
				drive.car.mdn.containsIgnoreCase(search)
					.or(drive.car.carPlate.containsIgnoreCase(search))
			);
		}

		return builder;
	}

}
