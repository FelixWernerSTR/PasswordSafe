package de.svi.svis5g.info.service.mapper;

import de.svi.svis5g.info.domain.*;
import de.svi.svis5g.info.service.dto.ThemaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Thema} and its DTO {@link ThemaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ThementypMapper.class })
public interface ThemaMapper extends EntityMapper<ThemaDTO, Thema> {
    @Mapping(target = "thementyp", source = "thementyp", qualifiedByName = "name")
    ThemaDTO toDto(Thema s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ThemaDTO toDtoName(Thema thema);
}
