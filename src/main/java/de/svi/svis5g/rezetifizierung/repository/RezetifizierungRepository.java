package de.svi.svis5g.rezetifizierung.repository;

import de.svi.svis5g.rezetifizierung.domain.Rezetifizierung;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  reactive repository for the Rezetifizierung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RezetifizierungRepository extends Repository<Rezetifizierung, Long> {}
