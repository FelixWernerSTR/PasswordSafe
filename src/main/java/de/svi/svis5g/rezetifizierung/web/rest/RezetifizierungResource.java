package de.svi.svis5g.rezetifizierung.web.rest;

import de.svi.svis5g.rezetifizierung.domain.Rezetifizierung;
import de.svi.svis5g.rezetifizierung.repository.RezetifizierungRepository;
import de.svi.svis5g.rezetifizierung.service.RezetifizierungService;
import de.svi.svis5g.rezetifizierung.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link de.svi.svis5g.rezetifizierung.domain.Rezetifizierung}.
 */
@RestController
@RequestMapping("/api")
public class RezetifizierungResource {

    private final Logger log = LoggerFactory.getLogger(RezetifizierungResource.class);

    private static final String ENTITY_NAME = "rezetifizierung";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RezetifizierungService rezetifizierungService;

    private final RezetifizierungRepository rezetifizierungRepository;

    public RezetifizierungResource(RezetifizierungService rezetifizierungService, RezetifizierungRepository rezetifizierungRepository) {
        this.rezetifizierungService = rezetifizierungService;
        this.rezetifizierungRepository = rezetifizierungRepository;
    }

    /**
     * {@code POST  /rezetifizierungs} : Create a new rezetifizierung.
     *
     * @param rezetifizierung the rezetifizierung to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rezetifizierung, or with status {@code 400 (Bad Request)} if the rezetifizierung has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rezetifizierungs")
    public Mono<ResponseEntity<Rezetifizierung>> createRezetifizierung(@Valid @RequestBody Rezetifizierung rezetifizierung)
        throws URISyntaxException {
        log.debug("REST request to save Rezetifizierung : {}", rezetifizierung);
        if (rezetifizierung.getId() != null) {
            throw new BadRequestAlertException("A new rezetifizierung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return rezetifizierungService
            .save(rezetifizierung)
            .map(
                result -> {
                    try {
                        return ResponseEntity
                            .created(new URI("/api/rezetifizierungs/" + result.getId()))
                            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            );
    }

    /**
     * {@code PUT  /rezetifizierungs/:id} : Updates an existing rezetifizierung.
     *
     * @param id the id of the rezetifizierung to save.
     * @param rezetifizierung the rezetifizierung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rezetifizierung,
     * or with status {@code 400 (Bad Request)} if the rezetifizierung is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rezetifizierung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rezetifizierungs/{id}")
    public Mono<ResponseEntity<Rezetifizierung>> updateRezetifizierung(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Rezetifizierung rezetifizierung
    ) throws URISyntaxException {
        log.debug("REST request to update Rezetifizierung : {}, {}", id, rezetifizierung);
        if (rezetifizierung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rezetifizierung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return rezetifizierungRepository
            .existsById(id)
            .flatMap(
                exists -> {
                    if (!exists) {
                        return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                    }

                    return rezetifizierungService
                        .save(rezetifizierung)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                        .map(
                            result ->
                                ResponseEntity
                                    .ok()
                                    .headers(
                                        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString())
                                    )
                                    .body(result)
                        );
                }
            );
    }

    /**
     * {@code PATCH  /rezetifizierungs/:id} : Partial updates given fields of an existing rezetifizierung, field will ignore if it is null
     *
     * @param id the id of the rezetifizierung to save.
     * @param rezetifizierung the rezetifizierung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rezetifizierung,
     * or with status {@code 400 (Bad Request)} if the rezetifizierung is not valid,
     * or with status {@code 404 (Not Found)} if the rezetifizierung is not found,
     * or with status {@code 500 (Internal Server Error)} if the rezetifizierung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rezetifizierungs/{id}", consumes = "application/merge-patch+json")
    public Mono<ResponseEntity<Rezetifizierung>> partialUpdateRezetifizierung(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Rezetifizierung rezetifizierung
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rezetifizierung partially : {}, {}", id, rezetifizierung);
        if (rezetifizierung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rezetifizierung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return rezetifizierungRepository
            .existsById(id)
            .flatMap(
                exists -> {
                    if (!exists) {
                        return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                    }

                    Mono<Rezetifizierung> result = rezetifizierungService.partialUpdate(rezetifizierung);

                    return result
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                        .map(
                            res ->
                                ResponseEntity
                                    .ok()
                                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                                    .body(res)
                        );
                }
            );
    }

    /**
     * {@code GET  /rezetifizierungs} : get all the rezetifizierungs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rezetifizierungs in body.
     */
    @GetMapping("/rezetifizierungs")
    public Mono<List<Rezetifizierung>> getAllRezetifizierungs() {
        log.debug("REST request to get all Rezetifizierungs");
        return rezetifizierungService.findAll().collectList();
    }

    /**
     * {@code GET  /rezetifizierungs} : get all the rezetifizierungs as a stream.
     * @return the {@link Flux} of rezetifizierungs.
     */
    @GetMapping(value = "/rezetifizierungs", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Rezetifizierung> getAllRezetifizierungsAsStream() {
        log.debug("REST request to get all Rezetifizierungs as a stream");
        return rezetifizierungService.findAll();
    }

    /**
     * {@code GET  /rezetifizierungs/:id} : get the "id" rezetifizierung.
     *
     * @param id the id of the rezetifizierung to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rezetifizierung, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rezetifizierungs/{id}")
    public Mono<ResponseEntity<Rezetifizierung>> getRezetifizierung(@PathVariable Long id) {
        log.debug("REST request to get Rezetifizierung : {}", id);
        Mono<Rezetifizierung> rezetifizierung = rezetifizierungService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rezetifizierung);
    }

    /**
     * {@code DELETE  /rezetifizierungs/:id} : delete the "id" rezetifizierung.
     *
     * @param id the id of the rezetifizierung to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rezetifizierungs/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteRezetifizierung(@PathVariable Long id) {
        log.debug("REST request to delete Rezetifizierung : {}", id);
        return rezetifizierungService
            .delete(id)
            .map(
                result ->
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
            );
    }
}
