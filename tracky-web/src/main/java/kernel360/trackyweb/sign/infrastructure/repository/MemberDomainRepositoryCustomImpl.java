package kernel360.trackyweb.sign.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static kernel360.trackycore.core.domain.entity.QMemberEntity.memberEntity;
import static kernel360.trackycore.core.domain.entity.QBizEntity.bizEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.MemberEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDomainRepositoryCustomImpl implements MemberDomainRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<MemberEntity> findByBizNameOrAdmin(String search, Pageable pageable) {
		BooleanExpression condition = containsBizNameOrAdmin(search);

		List<MemberEntity> content = jpaQueryFactory
			.selectFrom(memberEntity)
			.join(memberEntity.biz, bizEntity).fetchJoin()
			.where(condition)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = jpaQueryFactory
			.select(memberEntity.count())
			.from(memberEntity)
			.join(memberEntity.biz, bizEntity)
			.where(condition)
			.fetchOne();

		return new PageImpl<>(content, pageable, Optional.ofNullable(total).orElse(0L));
	}

	private BooleanExpression containsBizNameOrAdmin(String search) {
		if (StringUtils.isBlank(search)) {
			return null;
		}
		return bizEntity.bizName.containsIgnoreCase(search)
			.or(bizEntity.bizAdmin.containsIgnoreCase(search));
	}
}
