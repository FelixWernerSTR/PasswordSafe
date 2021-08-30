package de.svi.svis5g.info.repository;

import de.svi.svis5g.info.domain.Thementyp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Thementyp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThementypRepository extends JpaRepository<Thementyp, Long> {}
