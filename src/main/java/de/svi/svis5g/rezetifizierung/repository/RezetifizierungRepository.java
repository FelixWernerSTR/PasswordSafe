package de.svi.svis5g.rezetifizierung.repository;

import de.svi.svis5g.rezetifizierung.domain.Rezetifizierung;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rezetifizierung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RezetifizierungRepository extends JpaRepository<Rezetifizierung, Long> {}
