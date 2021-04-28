package de.svi.svis5g.info.service;

import de.svi.svis5g.info.service.dto.BeitragDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link de.svi.svis5g.info.domain.Beitrag}.
 */
public interface BeitragService {
    /**
     * Save a beitrag.
     *
     * @param beitragDTO the entity to save.
     * @return the persisted entity.
     */
    BeitragDTO save(BeitragDTO beitragDTO);

    /**
     * Partially updates a beitrag.
     *
     * @param beitragDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BeitragDTO> partialUpdate(BeitragDTO beitragDTO);

    /**
     * Get all the beitrags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BeitragDTO> findAll(Pageable pageable);

    /**
     * Get the "id" beitrag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BeitragDTO> findOne(Long id);

    /**
     * Delete the "id" beitrag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
