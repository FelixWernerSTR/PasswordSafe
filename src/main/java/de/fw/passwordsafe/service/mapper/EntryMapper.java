package de.fw.passwordsafe.service.mapper;

import de.fw.passwordsafe.domain.Entry;
import de.fw.passwordsafe.service.dto.EntryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entry} and its DTO {@link EntryDTO}.
 */
@Mapper(componentModel = "spring", uses = { GroupMapper.class })
public interface EntryMapper extends EntityMapper<EntryDTO, Entry> {
    @Mapping(target = "group", source = "group", qualifiedByName = "name")
    EntryDTO toDto(Entry s);
}
