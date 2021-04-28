package de.fw.passwordsafe.service;

import de.fw.passwordsafe.service.dto.GroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link de.fw.passwordsafe.domain.Group}.
 */
public interface GroupService {
    /**
     * Save a group.
     *
     * @param groupDTO the entity to save.
     * @return the persisted entity.
     */
    GroupDTO save(GroupDTO groupDTO);

    /**
     * Partially updates a group.
     *
     * @param groupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GroupDTO> partialUpdate(GroupDTO groupDTO);

    /**
     * Get all the groups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" group.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupDTO> findOne(Long id);

    /**
     * Delete the "id" group.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
