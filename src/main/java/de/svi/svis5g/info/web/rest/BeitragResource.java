package de.svi.svis5g.info.web.rest;

import de.svi.svis5g.info.repository.BeitragRepository;
import de.svi.svis5g.info.service.BeitragService;
import de.svi.svis5g.info.service.dto.BeitragDTO;
import de.svi.svis5g.info.web.rest.errors.BadRequestAlertException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link de.svi.svis5g.info.domain.Beitrag}.
 */
@RestController
@RequestMapping("/api")
public class BeitragResource {

    private final Logger log = LoggerFactory.getLogger(BeitragResource.class);

    private static final String ENTITY_NAME = "beitrag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeitragService beitragService;

    private final BeitragRepository beitragRepository;

    public BeitragResource(BeitragService beitragService, BeitragRepository beitragRepository) {
        this.beitragService = beitragService;
        this.beitragRepository = beitragRepository;
    }

    /**
     * {@code POST  /beitrags} : Create a new beitrag.
     *
     * @param beitragDTO the beitragDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beitragDTO, or with status {@code 400 (Bad Request)} if the beitrag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beitrags")
    public ResponseEntity<BeitragDTO> createBeitrag(@Valid @RequestBody BeitragDTO beitragDTO) throws URISyntaxException {
        log.debug("REST request to save Beitrag : {}", beitragDTO);
        if (beitragDTO.getId() != null) {
            throw new BadRequestAlertException("A new beitrag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BeitragDTO result = beitragService.save(beitragDTO);
        return ResponseEntity
            .created(new URI("/api/beitrags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beitrags/:id} : Updates an existing beitrag.
     *
     * @param id the id of the beitragDTO to save.
     * @param beitragDTO the beitragDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beitragDTO,
     * or with status {@code 400 (Bad Request)} if the beitragDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beitragDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beitrags/{id}")
    public ResponseEntity<BeitragDTO> updateBeitrag(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BeitragDTO beitragDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Beitrag : {}, {}", id, beitragDTO);
        if (beitragDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beitragDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beitragRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BeitragDTO result = beitragService.save(beitragDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beitragDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /beitrags/:id} : Partial updates given fields of an existing beitrag, field will ignore if it is null
     *
     * @param id the id of the beitragDTO to save.
     * @param beitragDTO the beitragDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beitragDTO,
     * or with status {@code 400 (Bad Request)} if the beitragDTO is not valid,
     * or with status {@code 404 (Not Found)} if the beitragDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the beitragDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/beitrags/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BeitragDTO> partialUpdateBeitrag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BeitragDTO beitragDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beitrag partially : {}, {}", id, beitragDTO);
        if (beitragDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beitragDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beitragRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BeitragDTO> result = beitragService.partialUpdate(beitragDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beitragDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /beitrags} : get all the beitrags.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beitrags in body.
     */
    @GetMapping("/beitrags")
    public ResponseEntity<List<BeitragDTO>> getAllBeitrags(Pageable pageable) {
        log.debug("REST request to get a page of Beitrags");
        Page<BeitragDTO> page = beitragService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /beitrags/:id} : get the "id" beitrag.
     *
     * @param id the id of the beitragDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beitragDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beitrags/{id}")
    public ResponseEntity<BeitragDTO> getBeitrag(@PathVariable Long id) {
        log.debug("REST request to get Beitrag : {}", id);
        Optional<BeitragDTO> beitragDTO = beitragService.findOne(id);
        return ResponseUtil.wrapOrNotFound(beitragDTO);
    }

    /**
     * {@code DELETE  /beitrags/:id} : delete the "id" beitrag.
     *
     * @param id the id of the beitragDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beitrags/{id}")
    public ResponseEntity<Void> deleteBeitrag(@PathVariable Long id) {
        log.debug("REST request to delete Beitrag : {}", id);
        beitragService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
