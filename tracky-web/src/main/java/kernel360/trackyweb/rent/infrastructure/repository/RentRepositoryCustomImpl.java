package kernel360.trackyweb.rent.infrastructure.repository;

import static kernel360.trackycore.core.domain.entity.QRentEntity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.QRentEntity;
import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.RentStatus;
import kernel360.trackyweb.admin.search.application.request.AdminRentSearchByFilterRequest;
import kernel360.trackyweb.rent.application.dto.request.RentSearchByFilterRequest;
import kernel360.trackyweb.rent.application.dto.response.RentMdnResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RentRepositoryCustomImpl implements RentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<RentEntity> searchRentByFilterAdmin(AdminRentSearchByFilterRequest request) {
		Pageable pageable = request.toPageable();

		BooleanBuilder builder = new BooleanBuilder()
			.and(isContainsBizName(request.bizSearch()))
			.and(isContainsRentUuid(request.rentUuid()))
			.and(isEqualRentStatus(request.status()))
			.and(isOverlapRentDate(request.rentDate()))
			.and(isNotDeleted(request.status()));

		JPAQuery<RentEntity> query = queryFactory
			.selectFrom(rentEntity)
			.where(builder)
			.orderBy(rentEntity.updatedAt.coalesce(rentEntity.createdAt).desc());

		List<RentEntity> content = fetchPagedContent(query, pageable);

		long total = Optional.ofNullable(
			queryFactory
				.select(rentEntity.count())
				.from(rentEntity)
				.where(builder)
				.fetchOne()
		).orElse(0L);

		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public Page<RentEntity> searchRentByFilter(RentSearchByFilterRequest request, String bizUuid) {
		Pageable pageable = request.toPageable();

		BooleanBuilder builder = new BooleanBuilder()
			.and(rentEntity.car.biz.bizUuid.eq(bizUuid))
			.and(isContainsRentUuid(request.rentUuid()))
			.and(isEqualRentStatus(request.toRentStatus()))
			.and(isOverlapRentDate(request.rentDate()))
			.and(isNotDeleted(request.toRentStatus()));

		JPAQuery<RentEntity> query = queryFactory
			.selectFrom(rentEntity)
			.where(builder)
			.orderBy(rentEntity.updatedAt.coalesce(rentEntity.createdAt).desc());

		List<RentEntity> content = fetchPagedContent(query, pageable);

		long total = Optional.ofNullable(
			queryFactory
				.select(rentEntity.count())
				.from(rentEntity)
				.where(builder)
				.fetchOne()
		).orElse(0L);

		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public List<RentMdnResponse> findRentableMdn(Long bizId) {

		return queryFactory
			.select(
				Projections.constructor(
					RentMdnResponse.class,
					rentEntity.car.mdn, rentEntity.car.status
				))
			.distinct()
			.from(rentEntity)
			.where(
				rentEntity.car.biz.id.eq(bizId),
				rentEntity.car.status.ne(CarStatus.CLOSED),
				rentEntity.car.status.ne(CarStatus.DELETED)
			)
			.fetch();
	}

	private BooleanExpression isContainsBizName(String bizSearch) {
		if (StringUtils.isBlank(bizSearch)) {
			return null;
		}
		return rentEntity.car.biz.bizName.containsIgnoreCase(bizSearch);
	}

	private BooleanBuilder isNotDeleted(RentStatus status) {
		if (status == RentStatus.DELETED) {
			return new BooleanBuilder();
		}
		return new BooleanBuilder(rentEntity.rentStatus.ne(RentStatus.DELETED));
	}

	private BooleanBuilder isContainsRentUuid(String rentUuid) {
		if (rentUuid == null || rentUuid.isBlank()) {
			return null;
		}
		return new BooleanBuilder(rentEntity.rentUuid.contains(rentUuid));
	}

	private BooleanBuilder isEqualRentStatus(RentStatus rentStatus) {
		if (rentStatus == null) {
			return null;
		}
		return new BooleanBuilder(rentEntity.rentStatus.eq(rentStatus));
	}

	private BooleanBuilder isOverlapRentDate(LocalDate rentDate) {
		if (rentDate == null) {
			return null;
		}
		LocalDateTime startOfDay = rentDate.atStartOfDay();
		LocalDateTime endOfDay = rentDate.atTime(23, 59, 59, 999_999_999);

		return new BooleanBuilder(
			rentEntity.rentStime.loe(endOfDay)
				.and(rentEntity.rentEtime.goe(startOfDay))
		);
	}

	private List<RentEntity> fetchPagedContent(JPAQuery<RentEntity> query, Pageable pageable) {
		return query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
	}

	@Override
	public List<RentEntity> findDelayedRents(String bizUuid, LocalDateTime now) {

		return queryFactory
			.selectFrom(rentEntity)
			.where(
				rentEntity.rentStatus.eq(RentStatus.RENTING),
				rentEntity.rentEtime.before(now),
				rentEntity.car.biz.bizUuid.eq(bizUuid)
			)
			.fetch();
	}

	@Override
	public List<RentEntity> findOverlappingRent(String mdn, LocalDateTime start, LocalDateTime end) {
		QRentEntity rent = QRentEntity.rentEntity;

		return queryFactory
			.selectFrom(rent)
			.where(
				rent.car.mdn.eq(mdn),
				rent.rentStatus.notIn(RentStatus.CANCELED, RentStatus.DELETED),
				rent.rentStime.lt(end),
				rent.rentEtime.gt(start)
			)
			.fetch();
	}
}
