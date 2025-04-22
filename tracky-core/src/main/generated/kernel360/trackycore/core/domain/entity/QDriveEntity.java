package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDriveEntity is a Querydsl query type for DriveEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDriveEntity extends EntityPathBase<DriveEntity> {

    private static final long serialVersionUID = -401398400L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDriveEntity driveEntity = new QDriveEntity("driveEntity");

    public final kernel360.trackycore.core.domain.entity.base.QDateBaseEntity _super = new kernel360.trackycore.core.domain.entity.base.QDateBaseEntity(this);

    public final QCarEntity car;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Double> driveDistance = createNumber("driveDistance", Double.class);

    public final DateTimePath<java.time.LocalDateTime> driveOffTime = createDateTime("driveOffTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> driveOnTime = createDateTime("driveOnTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLocationEntity location;

    public final StringPath memo = createString("memo");

    public final QRentEntity rent;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QDriveEntity(String variable) {
        this(DriveEntity.class, forVariable(variable), INITS);
    }

    public QDriveEntity(Path<? extends DriveEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDriveEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDriveEntity(PathMetadata metadata, PathInits inits) {
        this(DriveEntity.class, metadata, inits);
    }

    public QDriveEntity(Class<? extends DriveEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.car = inits.isInitialized("car") ? new QCarEntity(forProperty("car"), inits.get("car")) : null;
        this.location = inits.isInitialized("location") ? new QLocationEntity(forProperty("location")) : null;
        this.rent = inits.isInitialized("rent") ? new QRentEntity(forProperty("rent"), inits.get("rent")) : null;
    }

}

