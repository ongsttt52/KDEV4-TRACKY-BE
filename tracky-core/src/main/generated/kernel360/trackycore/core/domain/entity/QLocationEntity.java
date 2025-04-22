package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationEntity is a Querydsl query type for LocationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationEntity extends EntityPathBase<LocationEntity> {

    private static final long serialVersionUID = -1053364315L;

    public static final QLocationEntity locationEntity = new QLocationEntity("locationEntity");

    public final kernel360.trackycore.core.domain.entity.base.QDateBaseEntity _super = new kernel360.trackycore.core.domain.entity.base.QDateBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> driveEndLat = createNumber("driveEndLat", Integer.class);

    public final NumberPath<Integer> driveEndLon = createNumber("driveEndLon", Integer.class);

    public final NumberPath<Integer> driveStartLat = createNumber("driveStartLat", Integer.class);

    public final NumberPath<Integer> driveStartLon = createNumber("driveStartLon", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QLocationEntity(String variable) {
        super(LocationEntity.class, forVariable(variable));
    }

    public QLocationEntity(Path<? extends LocationEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationEntity(PathMetadata metadata) {
        super(LocationEntity.class, metadata);
    }

}

