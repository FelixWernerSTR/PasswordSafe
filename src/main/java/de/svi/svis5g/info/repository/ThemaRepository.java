package de.svi.svis5g.info.repository;

import de.svi.svis5g.info.domain.Thema;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Thema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThemaRepository extends JpaRepository<Thema, Long> {}
