package de.svi.svis5g.rezetifizierung.service;

import de.svi.svis5g.rezetifizierung.domain.Rezetifizierung;
import de.svi.svis5g.rezetifizierung.repository.RezetifizierungRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Rezetifizierung}.
 */
@Service
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
    public Mono<Rezetifizierung> save(Rezetifizierung rezetifizierung) {
        log.debug("Request to save Rezetifizierung : {}", rezetifizierung);
        return rezetifizierungRepository.save(rezetifizierung);
    }

    /**
     * Partially update a rezetifizierung.
     *
     * @param rezetifizierung the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Rezetifizierung> partialUpdate(Rezetifizierung rezetifizierung) {
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
            .flatMap(rezetifizierungRepository::save);
    }

    /**
     * Get all the rezetifizierungs.
     *
     * @return the list of entities.
     */
    public Flux<Rezetifizierung> findAll() {
        log.debug("Request to get all Rezetifizierungs");
        return rezetifizierungRepository.findAll();
    }

    /**
     * Returns the number of rezetifizierungs available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return rezetifizierungRepository.count();
    }

    /**
     * Get one rezetifizierung by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<Rezetifizierung> findOne(Long id) {
        log.debug("Request to get Rezetifizierung : {}", id);
        return rezetifizierungRepository.findById(id);
    }

    /**
     * Delete the rezetifizierung by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Rezetifizierung : {}", id);
        return rezetifizierungRepository.deleteById(id);
    }
}
