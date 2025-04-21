package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarEntity is a Querydsl query type for CarEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarEntity extends EntityPathBase<CarEntity> {

    private static final long serialVersionUID = 1093201162L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarEntity carEntity = new QCarEntity("carEntity");

    public final kernel360.trackycore.core.domain.entity.base.QDateBaseEntity _super = new kernel360.trackycore.core.domain.entity.base.QDateBaseEntity(this);

    public final QBizEntity biz;

    public final StringPath carName = createString("carName");

    public final StringPath carPlate = createString("carPlate");

    public final StringPath carType = createString("carType");

    public final StringPath carYear = createString("carYear");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final QDeviceEntity device;

    public final StringPath mdn = createString("mdn");

    public final StringPath purpose = createString("purpose");

    public final StringPath status = createString("status");

    public final NumberPath<Double> sum = createNumber("sum", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCarEntity(String variable) {
        this(CarEntity.class, forVariable(variable), INITS);
    }

    public QCarEntity(Path<? extends CarEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarEntity(PathMetadata metadata, PathInits inits) {
        this(CarEntity.class, metadata, inits);
    }

    public QCarEntity(Class<? extends CarEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.biz = inits.isInitialized("biz") ? new QBizEntity(forProperty("biz")) : null;
        this.device = inits.isInitialized("device") ? new QDeviceEntity(forProperty("device")) : null;
    }

}

