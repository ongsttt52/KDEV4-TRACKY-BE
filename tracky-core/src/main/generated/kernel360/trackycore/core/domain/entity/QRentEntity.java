package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRentEntity is a Querydsl query type for RentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRentEntity extends EntityPathBase<RentEntity> {

    private static final long serialVersionUID = 1439553193L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRentEntity rentEntity = new QRentEntity("rentEntity");

    public final kernel360.trackycore.core.domain.entity.base.QDateBaseEntity _super = new kernel360.trackycore.core.domain.entity.base.QDateBaseEntity(this);

    public final QCarEntity car;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath purpose = createString("purpose");

    public final StringPath renterName = createString("renterName");

    public final StringPath renterPhone = createString("renterPhone");

    public final DateTimePath<java.time.LocalDateTime> rentEtime = createDateTime("rentEtime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> rentLat = createNumber("rentLat", Integer.class);

    public final StringPath rentLoc = createString("rentLoc");

    public final NumberPath<Integer> rentLon = createNumber("rentLon", Integer.class);

    public final StringPath rentStatus = createString("rentStatus");

    public final DateTimePath<java.time.LocalDateTime> rentStime = createDateTime("rentStime", java.time.LocalDateTime.class);

    public final StringPath rentUuid = createString("rentUuid");

    public final NumberPath<Integer> returnLat = createNumber("returnLat", Integer.class);

    public final StringPath returnLoc = createString("returnLoc");

    public final NumberPath<Integer> returnLon = createNumber("returnLon", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRentEntity(String variable) {
        this(RentEntity.class, forVariable(variable), INITS);
    }

    public QRentEntity(Path<? extends RentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRentEntity(PathMetadata metadata, PathInits inits) {
        this(RentEntity.class, metadata, inits);
    }

    public QRentEntity(Class<? extends RentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.car = inits.isInitialized("car") ? new QCarEntity(forProperty("car"), inits.get("car")) : null;
    }

}

