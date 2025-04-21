package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeviceEntity is a Querydsl query type for DeviceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeviceEntity extends EntityPathBase<DeviceEntity> {

    private static final long serialVersionUID = -239847226L;

    public static final QDeviceEntity deviceEntity = new QDeviceEntity("deviceEntity");

    public final kernel360.trackycore.core.domain.entity.base.QDateBaseEntity _super = new kernel360.trackycore.core.domain.entity.base.QDateBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath did = createString("did");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mid = createString("mid");

    public final StringPath pv = createString("pv");

    public final StringPath tid = createString("tid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QDeviceEntity(String variable) {
        super(DeviceEntity.class, forVariable(variable));
    }

    public QDeviceEntity(Path<? extends DeviceEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeviceEntity(PathMetadata metadata) {
        super(DeviceEntity.class, metadata);
    }

}

