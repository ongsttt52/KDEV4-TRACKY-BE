package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCarEventEntity is a Querydsl query type for CarEventEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarEventEntity extends EntityPathBase<CarEventEntity> {

    private static final long serialVersionUID = 1554710102L;

    public static final QCarEventEntity carEventEntity = new QCarEventEntity("carEventEntity");

    public final DateTimePath<java.time.LocalDateTime> eventAt = createDateTime("eventAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mdn = createString("mdn");

    public final StringPath type = createString("type");

    public QCarEventEntity(String variable) {
        super(CarEventEntity.class, forVariable(variable));
    }

    public QCarEventEntity(Path<? extends CarEventEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCarEventEntity(PathMetadata metadata) {
        super(CarEventEntity.class, metadata);
    }

}

