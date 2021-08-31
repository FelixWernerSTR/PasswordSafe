package de.svi.svis5g.rezetifizierung.service;

import de.svi.svis5g.rezetifizierung.domain.Rezetifizierung;
import de.svi.svis5g.rezetifizierung.repository.RezetifizierungRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rezetifizierung}.
 */
@Service
@Transactional
public class RezetifizierungService {

    private final Logger log = LoggerFactory.getLogger(RezetifizierungService.class);

    private final RezetifizierungRepository rezetifizierungRepository;

    public RezetifizierungService(RezetifizierungRepository rezetifizierungRepository) {
        this.rezetifizierungRepository = rezetifizierungRepository;
    }

    /**
     * Save a rezetifizierung.
     *
     * @param rezetifizierung the entity to save.
     * @return the persisted entity.
     */
    public Rezetifizierung save(Rezetifizierung rezetifizierung) {
        log.debug("Request to save Rezetifizierung : {}", rezetifizierung);
        return rezetifizierungRepository.save(rezetifizierung);
    }

    /**
     * Partially update a rezetifizierung.
     *
     * @param rezetifizierung the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Rezetifizierung> partialUpdate(Rezetifizierung rezetifizierung) {
        log.debug("Request to partially update Rezetifizierung : {}", rezetifizierung);

        return rezetifizierungRepository
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
    }

    /**
     * Get all the rezetifizierungs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Rezetifizierung> findAll(Pageable pageable) {
        log.debug("Request to get all Rezetifizierungs");
        return rezetifizierungRepository.findAll(pageable);
    }

    /**
     * Get one rezetifizierung by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Rezetifizierung> findOne(Long id) {
        log.debug("Request to get Rezetifizierung : {}", id);
        return rezetifizierungRepository.findById(id);
    }

    /**
     * Delete the rezetifizierung by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rezetifizierung : {}", id);
        rezetifizierungRepository.deleteById(id);
    }
}
