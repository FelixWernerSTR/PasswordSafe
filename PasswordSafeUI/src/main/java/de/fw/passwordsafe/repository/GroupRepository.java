package de.fw.passwordsafe.repository;

import de.fw.passwordsafe.domain.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Group entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupRepository extends ReactiveCrudRepository<Group, Long>, GroupRepositoryInternal {
    Flux<Group> findAllBy(Pageable pageable);

    @Override
    <S extends Group> Mono<S> save(S entity);

    @Override
    Flux<Group> findAll();

    @Override
    Mono<Group> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface GroupRepositoryInternal {
    <S extends Group> Mono<S> save(S entity);

    Flux<Group> findAllBy(Pageable pageable);

    Flux<Group> findAll();

    Mono<Group> findById(Long id);

    Flux<Group> findAllBy(Pageable pageable, Criteria criteria);
}
