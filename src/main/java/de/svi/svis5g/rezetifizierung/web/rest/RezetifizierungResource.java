package de.svi.svis5g.rezetifizierung.web.rest;

import de.svi.svis5g.rezetifizierung.domain.Rezetifizierung;
import de.svi.svis5g.rezetifizierung.repository.RezetifizierungRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link de.svi.svis5g.rezetifizierung.domain.Rezetifizierung}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RezetifizierungResource {

    private final Logger log = LoggerFactory.getLogger(RezetifizierungResource.class);

    private static final String ENTITY_NAME = "rezetifizierung";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RezetifizierungRepository rezetifizierungRepository;

    public RezetifizierungResource(RezetifizierungRepository rezetifizierungRepository) {
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
    public ResponseEntity<Rezetifizierung> createRezetifizierung(@Valid @RequestBody Rezetifizierung rezetifizierung)
        throws URISyntaxException {
        log.debug("REST request to save Rezetifizierung : {}", rezetifizierung);
        if (rezetifizierung.getId() != null) {
            throw new BadRequestAlertException("A new rezetifizierung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rezetifizierung result = rezetifizierungRepository.save(rezetifizierung);
        return ResponseEntity
            .created(new URI("/api/rezetifizierungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    public ResponseEntity<Rezetifizierung> updateRezetifizierung(
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

        if (!rezetifizierungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Rezetifizierung result = rezetifizierungRepository.save(rezetifizierung);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rezetifizierung.getId().toString()))
            .body(result);
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
    public ResponseEntity<Rezetifizierung> partialUpdateRezetifizierung(
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

        if (!rezetifizierungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rezetifizierung> result = rezetifizierungRepository
            .findById(rezetifizierung.getId())
            .map(
                existingRezetifizierung -> {
                    if (rezetifizierung.getLoginName() != null) {
                        existingRezetifizierung.setLoginName(rezetifizierung.getLoginName());
                    }
                    if (rezetifizierung.getNachname() != null) {
                        existingRezetifizierung.setNachname(rezetifizierung.getNachname());
                    }
                    if (rezetifizierung.getVorname() != null) {
                        existingRezetifizierung.setVorname(rezetifizierung.getVorname());
                    }
                    if (rezetifizierung.getVermittlerNummer() != null) {
                        existingRezetifizierung.setVermittlerNummer(rezetifizierung.getVermittlerNummer());
                    }
                    if (rezetifizierung.getRollenZugehoerigkeiten() != null) {
                        existingRezetifizierung.setRollenZugehoerigkeiten(rezetifizierung.getRollenZugehoerigkeiten());
                    }
                    if (rezetifizierung.getDvcVertreterNummer() != null) {
                        existingRezetifizierung.setDvcVertreterNummer(rezetifizierung.getDvcVertreterNummer());
                    }
                    if (rezetifizierung.getDvcBenutzerGruppe() != null) {
                        existingRezetifizierung.setDvcBenutzerGruppe(rezetifizierung.getDvcBenutzerGruppe());
                    }
                    if (rezetifizierung.getIcisSrGebaude() != null) {
                        existingRezetifizierung.setIcisSrGebaude(rezetifizierung.getIcisSrGebaude());
                    }
                    if (rezetifizierung.getIcisSrHaftpflicht() != null) {
                        existingRezetifizierung.setIcisSrHaftpflicht(rezetifizierung.getIcisSrHaftpflicht());
                    }
                    if (rezetifizierung.getIcisSrLeitungswasser() != null) {
                        existingRezetifizierung.setIcisSrLeitungswasser(rezetifizierung.getIcisSrLeitungswasser());
                    }
                    if (rezetifizierung.getIcisSrKfzKasko() != null) {
                        existingRezetifizierung.setIcisSrKfzKasko(rezetifizierung.getIcisSrKfzKasko());
                    }

                    return existingRezetifizierung;
                }
            )
            .map(rezetifizierungRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rezetifizierung.getId().toString())
        );
    }

    /**
     * {@code GET  /rezetifizierungs} : get all the rezetifizierungs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rezetifizierungs in body.
     */
    @GetMapping("/rezetifizierungs")
    public ResponseEntity<List<Rezetifizierung>> getAllRezetifizierungs(Pageable pageable) {
        log.debug("REST request to get a page of Rezetifizierungs");
        Page<Rezetifizierung> page = rezetifizierungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rezetifizierungs/:id} : get the "id" rezetifizierung.
     *
     * @param id the id of the rezetifizierung to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rezetifizierung, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rezetifizierungs/{id}")
    public ResponseEntity<Rezetifizierung> getRezetifizierung(@PathVariable Long id) {
        log.debug("REST request to get Rezetifizierung : {}", id);
        Optional<Rezetifizierung> rezetifizierung = rezetifizierungRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rezetifizierung);
    }

    /**
     * {@code DELETE  /rezetifizierungs/:id} : delete the "id" rezetifizierung.
     *
     * @param id the id of the rezetifizierung to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rezetifizierungs/{id}")
    public ResponseEntity<Void> deleteRezetifizierung(@PathVariable Long id) {
        log.debug("REST request to delete Rezetifizierung : {}", id);
        rezetifizierungRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
