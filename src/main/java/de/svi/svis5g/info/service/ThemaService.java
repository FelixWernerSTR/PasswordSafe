package de.svi.svis5g.info.service;

import de.svi.svis5g.info.service.dto.ThemaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link de.svi.svis5g.info.domain.Thema}.
 */
public interface ThemaService {
    /**
     * Save a thema.
     *
     * @param themaDTO the entity to save.
     * @return the persisted entity.
     */
    ThemaDTO save(ThemaDTO themaDTO);

    /**
     * Partially updates a thema.
     *
     * @param themaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThemaDTO> partialUpdate(ThemaDTO themaDTO);

    /**
     * Get all the themas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThemaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" thema.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThemaDTO> findOne(Long id);

    /**
     * Delete the "id" thema.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
