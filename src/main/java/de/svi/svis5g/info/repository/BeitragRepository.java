package de.svi.svis5g.info.repository;

import de.svi.svis5g.info.domain.Beitrag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Beitrag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeitragRepository extends JpaRepository<Beitrag, Long> {}
