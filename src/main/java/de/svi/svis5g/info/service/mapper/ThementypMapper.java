package de.svi.svis5g.info.service.mapper;

import de.svi.svis5g.info.domain.*;
import de.svi.svis5g.info.service.dto.ThementypDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Thementyp} and its DTO {@link ThementypDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThementypMapper extends EntityMapper<ThementypDTO, Thementyp> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ThementypDTO toDtoName(Thementyp thementyp);
}
