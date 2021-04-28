package de.svi.svis5g.info.service.mapper;

import de.svi.svis5g.info.domain.*;
import de.svi.svis5g.info.service.dto.BeitragDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Beitrag} and its DTO {@link BeitragDTO}.
 */
@Mapper(componentModel = "spring", uses = { ThemaMapper.class })
public interface BeitragMapper extends EntityMapper<BeitragDTO, Beitrag> {
    @Mapping(target = "thema", source = "thema", qualifiedByName = "name")
    BeitragDTO toDto(Beitrag s);
}
