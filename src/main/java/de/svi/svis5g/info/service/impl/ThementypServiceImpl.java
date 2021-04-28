package de.svi.svis5g.info.service.impl;

import de.svi.svis5g.info.domain.Thementyp;
import de.svi.svis5g.info.repository.ThementypRepository;
import de.svi.svis5g.info.service.ThementypService;
import de.svi.svis5g.info.service.dto.ThementypDTO;
import de.svi.svis5g.info.service.mapper.ThementypMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Thementyp}.
 */
@Service
@Transactional
public class ThementypServiceImpl implements ThementypService {

    private final Logger log = LoggerFactory.getLogger(ThementypServiceImpl.class);

    private final ThementypRepository thementypRepository;

    private final ThementypMapper thementypMapper;

    public ThementypServiceImpl(ThementypRepository thementypRepository, ThementypMapper thementypMapper) {
        this.thementypRepository = thementypRepository;
        this.thementypMapper = thementypMapper;
    }

    @Override
    public ThementypDTO save(ThementypDTO thementypDTO) {
        log.debug("Request to save Thementyp : {}", thementypDTO);
        Thementyp thementyp = thementypMapper.toEntity(thementypDTO);
        thementyp = thementypRepository.save(thementyp);
        return thementypMapper.toDto(thementyp);
    }

    @Override
    public Optional<ThementypDTO> partialUpdate(ThementypDTO thementypDTO) {
        log.debug("Request to partially update Thementyp : {}", thementypDTO);

        return thementypRepository
            .findById(thementypDTO.getId())
            .map(
                existingThementyp -> {
                    thementypMapper.partialUpdate(existingThementyp, thementypDTO);
                    return existingThementyp;
                }
            )
            .map(thementypRepository::save)
            .map(thementypMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThementypDTO> findAll() {
        log.debug("Request to get all Thementyps");
        return thementypRepository.findAll().stream().map(thementypMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThementypDTO> findOne(Long id) {
        log.debug("Request to get Thementyp : {}", id);
        return thementypRepository.findById(id).map(thementypMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Thementyp : {}", id);
        thementypRepository.deleteById(id);
    }
}
