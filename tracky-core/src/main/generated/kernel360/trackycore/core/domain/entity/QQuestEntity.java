package kernel360.trackycore.core.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestEntity is a Querydsl query type for QuestEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestEntity extends EntityPathBase<QuestEntity> {

    private static final long serialVersionUID = -447429928L;

    public static final QQuestEntity questEntity = new QQuestEntity("questEntity");

    public final kernel360.trackycore.core.domain.entity.base.QDateBaseEntity _super = new kernel360.trackycore.core.domain.entity.base.QDateBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QQuestEntity(String variable) {
        super(QuestEntity.class, forVariable(variable));
    }

    public QQuestEntity(Path<? extends QuestEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestEntity(PathMetadata metadata) {
        super(QuestEntity.class, metadata);
    }

}

