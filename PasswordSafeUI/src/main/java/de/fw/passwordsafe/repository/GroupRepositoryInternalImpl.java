package de.fw.passwordsafe.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import de.fw.passwordsafe.domain.Group;
import de.fw.passwordsafe.repository.rowmapper.GroupRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive custom repository implementation for the Group entity.
 */
@SuppressWarnings("unused")
class GroupRepositoryInternalImpl extends SimpleR2dbcRepository<Group, Long> implements GroupRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final GroupRowMapper groupMapper;

    private static final Table entityTable = Table.aliased("jhi_group", EntityManager.ENTITY_ALIAS);

    public GroupRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        GroupRowMapper groupMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Group.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.groupMapper = groupMapper;
    }

    @Override
    public Flux<Group> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<Group> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<Group> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = GroupSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);

        String select = entityManager.createSelect(selectFrom, Group.class, pageable, criteria);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Group> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<Group> findById(Long id) {
        return createQuery(null, where(EntityManager.ENTITY_ALIAS + ".id").is(id)).one();
    }

    private Group process(Row row, RowMetadata metadata) {
        Group entity = groupMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Group> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
