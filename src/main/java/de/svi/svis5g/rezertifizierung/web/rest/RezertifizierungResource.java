package de.svi.svis5g.rezertifizierung.web.rest;

import de.svi.svis5g.rezertifizierung.domain.Rezertifizierung;
import de.svi.svis5g.rezertifizierung.repository.RezertifizierungRepository;
import de.svi.svis5g.rezertifizierung.service.RezertifizierungService;
import de.svi.svis5g.rezertifizierung.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link de.svi.svis5g.rezertifizierung.domain.Rezertifizierung}.
 */
@RestController
@RequestMapping("/api")
public class RezertifizierungResource {

    private final Logger log = LoggerFactory.getLogger(RezertifizierungResource.class);

    private static final String ENTITY_NAME = "rezertifizierung";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RezertifizierungService rezertifizierungService;

    private final RezertifizierungRepository rezertifizierungRepository;

    public RezertifizierungResource(
        RezertifizierungService rezertifizierungService,
        RezertifizierungRepository rezertifizierungRepository
    ) {
        this.rezertifizierungService = rezertifizierungService;
        this.rezertifizierungRepository = rezertifizierungRepository;
    }

    /**
     * {@code POST  /rezertifizierungs} : Create a new rezertifizierung.
     *
     * @param rezertifizierung the rezertifizierung to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rezertifizierung, or with status {@code 400 (Bad Request)} if the rezertifizierung has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rezertifizierungs")
    public Mono<ResponseEntity<Rezertifizierung>> createRezertifizierung(@Valid @RequestBody Rezertifizierung rezertifizierung)
        throws URISyntaxException {
        log.debug("REST request to save Rezertifizierung : {}", rezertifizierung);
        if (rezertifizierung.getId() != null) {
            throw new BadRequestAlertException("A new rezertifizierung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return rezertifizierungService
            .save(rezertifizierung)
            .map(
                result -> {
                    try {
                        return ResponseEntity
                            .created(new URI("/api/rezertifizierungs/" + result.getId()))
                            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            );
    }

    /**
     * {@code PUT  /rezertifizierungs/:id} : Updates an existing rezertifizierung.
     *
     * @param id the id of the rezertifizierung to save.
     * @param rezertifizierung the rezertifizierung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rezertifizierung,
     * or with status {@code 400 (Bad Request)} if the rezertifizierung is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rezertifizierung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rezertifizierungs/{id}")
    public Mono<ResponseEntity<Rezertifizierung>> updateRezertifizierung(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Rezertifizierung rezertifizierung
    ) throws URISyntaxException {
        log.debug("REST request to update Rezertifizierung : {}, {}", id, rezertifizierung);
        if (rezertifizierung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rezertifizierung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return rezertifizierungRepository
            .existsById(id)
            .flatMap(
                exists -> {
                    if (!exists) {
                        return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                    }

                    return rezertifizierungService
                        .save(rezertifizierung)
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
     * {@code PATCH  /rezertifizierungs/:id} : Partial updates given fields of an existing rezertifizierung, field will ignore if it is null
     *
     * @param id the id of the rezertifizierung to save.
     * @param rezertifizierung the rezertifizierung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rezertifizierung,
     * or with status {@code 400 (Bad Request)} if the rezertifizierung is not valid,
     * or with status {@code 404 (Not Found)} if the rezertifizierung is not found,
     * or with status {@code 500 (Internal Server Error)} if the rezertifizierung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rezertifizierungs/{id}", consumes = "application/merge-patch+json")
    public Mono<ResponseEntity<Rezertifizierung>> partialUpdateRezertifizierung(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Rezertifizierung rezertifizierung
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rezertifizierung partially : {}, {}", id, rezertifizierung);
        if (rezertifizierung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rezertifizierung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return rezertifizierungRepository
            .existsById(id)
            .flatMap(
                exists -> {
                    if (!exists) {
                        return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                    }

                    Mono<Rezertifizierung> result = rezertifizierungService.partialUpdate(rezertifizierung);

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
     * {@code GET  /rezertifizierungs} : get all the rezertifizierungs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rezertifizierungs in body.
     */
    @GetMapping("/rezertifizierungs")
    public Mono<List<Rezertifizierung>> getAllRezertifizierungs() {
        log.debug("REST request to get all Rezertifizierungs");
        return rezertifizierungService.findAll().collectList();
    }

    /**
     * {@code GET  /rezertifizierungs} : get all the rezertifizierungs as a stream.
     * @return the {@link Flux} of rezertifizierungs.
     */
    @GetMapping(value = "/rezertifizierungs", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Rezertifizierung> getAllRezertifizierungsAsStream() {
        log.debug("REST request to get all Rezertifizierungs as a stream");
        return rezertifizierungService.findAll();
    }

    /**
     * {@code GET  /rezertifizierungs/:id} : get the "id" rezertifizierung.
     *
     * @param id the id of the rezertifizierung to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rezertifizierung, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rezertifizierungs/{id}")
    public Mono<ResponseEntity<Rezertifizierung>> getRezertifizierung(@PathVariable Long id) {
        log.debug("REST request to get Rezertifizierung : {}", id);
        Mono<Rezertifizierung> rezertifizierung = rezertifizierungService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rezertifizierung);
    }

    /**
     * {@code DELETE  /rezertifizierungs/:id} : delete the "id" rezertifizierung.
     *
     * @param id the id of the rezertifizierung to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rezertifizierungs/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteRezertifizierung(@PathVariable Long id) {
        log.debug("REST request to delete Rezertifizierung : {}", id);
        return rezertifizierungService
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
