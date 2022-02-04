package de.fw.passwordsafe.service.mapper;

import de.fw.passwordsafe.domain.Group;
import de.fw.passwordsafe.service.dto.GroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Group} and its DTO {@link GroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupMapper extends EntityMapper<GroupDTO, Group> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GroupDTO toDtoName(Group group);
}
