package de.fw.passwordsafe.service;

import de.fw.passwordsafe.service.dto.EntryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link de.fw.passwordsafe.domain.Entry}.
 */
public interface EntryService {
    /**
     * Save a entry.
     *
     * @param entryDTO the entity to save.
     * @return the persisted entity.
     */
    EntryDTO save(EntryDTO entryDTO);

    /**
     * Partially updates a entry.
     *
     * @param entryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EntryDTO> partialUpdate(EntryDTO entryDTO);

    /**
     * Get all the entries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EntryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" entry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntryDTO> findOne(Long id);

    /**
     * Delete the "id" entry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
