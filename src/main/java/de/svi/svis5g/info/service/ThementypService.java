package de.svi.svis5g.info.service;

import de.svi.svis5g.info.service.dto.ThementypDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link de.svi.svis5g.info.domain.Thementyp}.
 */
public interface ThementypService {
    /**
     * Save a thementyp.
     *
     * @param thementypDTO the entity to save.
     * @return the persisted entity.
     */
    ThementypDTO save(ThementypDTO thementypDTO);

    /**
     * Partially updates a thementyp.
     *
     * @param thementypDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThementypDTO> partialUpdate(ThementypDTO thementypDTO);

    /**
     * Get all the thementyps.
     *
     * @return the list of entities.
     */
    List<ThementypDTO> findAll();

    /**
     * Get the "id" thementyp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThementypDTO> findOne(Long id);

    /**
     * Delete the "id" thementyp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
