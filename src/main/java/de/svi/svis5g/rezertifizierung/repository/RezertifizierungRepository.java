package de.svi.svis5g.rezertifizierung.repository;

import de.svi.svis5g.rezertifizierung.domain.Rezertifizierung;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  reactive repository for the Rezertifizierung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RezertifizierungRepository extends Repository<Rezertifizierung, Long> {}
