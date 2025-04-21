package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberEntity is a Querydsl query type for MemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberEntity extends EntityPathBase<MemberEntity> {

    private static final long serialVersionUID = 301097482L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberEntity memberEntity = new QMemberEntity("memberEntity");

    public final kernel360.trackycore.core.domain.entity.base.QDateBaseEntity _super = new kernel360.trackycore.core.domain.entity.base.QDateBaseEntity(this);

    public final QBizEntity bizId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deleteAt = createDateTime("deleteAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastLoginAt = createDateTime("lastLoginAt", java.time.LocalDateTime.class);

    public final StringPath memberId = createString("memberId");

    public final StringPath pwd = createString("pwd");

    public final StringPath role = createString("role");

    public final StringPath status = createString("status");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberEntity(String variable) {
        this(MemberEntity.class, forVariable(variable), INITS);
    }

    public QMemberEntity(Path<? extends MemberEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberEntity(PathMetadata metadata, PathInits inits) {
        this(MemberEntity.class, metadata, inits);
    }

    public QMemberEntity(Class<? extends MemberEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bizId = inits.isInitialized("bizId") ? new QBizEntity(forProperty("bizId")) : null;
    }

}

