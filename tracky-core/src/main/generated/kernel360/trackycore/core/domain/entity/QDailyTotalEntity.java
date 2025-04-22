package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDailyTotalEntity is a Querydsl query type for DailyTotalEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyTotalEntity extends EntityPathBase<DailyTotalEntity> {

    private static final long serialVersionUID = -273285925L;

    public static final QDailyTotalEntity dailyTotalEntity = new QDailyTotalEntity("dailyTotalEntity");

    public final StringPath dailyDistance = createString("dailyDistance");

    public final StringPath date = createString("date");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mdn = createString("mdn");

    public QDailyTotalEntity(String variable) {
        super(DailyTotalEntity.class, forVariable(variable));
    }

    public QDailyTotalEntity(Path<? extends DailyTotalEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDailyTotalEntity(PathMetadata metadata) {
        super(DailyTotalEntity.class, metadata);
    }

}

