package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGpsHistoryEntity is a Querydsl query type for GpsHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGpsHistoryEntity extends EntityPathBase<GpsHistoryEntity> {

    private static final long serialVersionUID = 584654202L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGpsHistoryEntity gpsHistoryEntity = new QGpsHistoryEntity("gpsHistoryEntity");

    public final NumberPath<Integer> ang = createNumber("ang", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QDriveEntity drive;

    public final NumberPath<Long> driveSeq = createNumber("driveSeq", Long.class);

    public final StringPath gcd = createString("gcd");

    public final NumberPath<Integer> lat = createNumber("lat", Integer.class);

    public final NumberPath<Integer> lon = createNumber("lon", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> oTime = createDateTime("oTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> spd = createNumber("spd", Integer.class);

    public final NumberPath<Double> sum = createNumber("sum", Double.class);

    public QGpsHistoryEntity(String variable) {
        this(GpsHistoryEntity.class, forVariable(variable), INITS);
    }

    public QGpsHistoryEntity(Path<? extends GpsHistoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGpsHistoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGpsHistoryEntity(PathMetadata metadata, PathInits inits) {
        this(GpsHistoryEntity.class, metadata, inits);
    }

    public QGpsHistoryEntity(Class<? extends GpsHistoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.drive = inits.isInitialized("drive") ? new QDriveEntity(forProperty("drive"), inits.get("drive")) : null;
    }

}

