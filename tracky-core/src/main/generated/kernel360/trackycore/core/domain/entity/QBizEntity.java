package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBizEntity is a Querydsl query type for BizEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBizEntity extends EntityPathBase<BizEntity> {

    private static final long serialVersionUID = -1826636023L;

    public static final QBizEntity bizEntity = new QBizEntity("bizEntity");

    public final kernel360.trackycore.core.domain.entity.base.QDateBaseEntity _super = new kernel360.trackycore.core.domain.entity.base.QDateBaseEntity(this);

    public final StringPath bizAdmin = createString("bizAdmin");

    public final StringPath bizName = createString("bizName");

    public final StringPath bizPhoneNum = createString("bizPhoneNum");

    public final StringPath bizRegNum = createString("bizRegNum");

    public final StringPath bizUuid = createString("bizUuid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deleteAt = createDateTime("deleteAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QBizEntity(String variable) {
        super(BizEntity.class, forVariable(variable));
    }

    public QBizEntity(Path<? extends BizEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBizEntity(PathMetadata metadata) {
        super(BizEntity.class, metadata);
    }

}

