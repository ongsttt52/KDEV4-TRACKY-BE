package kernel360.trackycore.core.domain.entity.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDateBaseEntity is a Querydsl query type for DateBaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QDateBaseEntity extends EntityPathBase<DateBaseEntity> {

    private static final long serialVersionUID = 730931666L;

    public static final QDateBaseEntity dateBaseEntity = new QDateBaseEntity("dateBaseEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QDateBaseEntity(String variable) {
        super(DateBaseEntity.class, forVariable(variable));
    }

    public QDateBaseEntity(Path<? extends DateBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDateBaseEntity(PathMetadata metadata) {
        super(DateBaseEntity.class, metadata);
    }

}

