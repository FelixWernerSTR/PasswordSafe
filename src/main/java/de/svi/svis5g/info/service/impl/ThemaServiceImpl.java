package de.svi.svis5g.info.service.impl;

import de.svi.svis5g.info.domain.Thema;
import de.svi.svis5g.info.repository.ThemaRepository;
import de.svi.svis5g.info.service.ThemaService;
import de.svi.svis5g.info.service.dto.ThemaDTO;
import de.svi.svis5g.info.service.mapper.ThemaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Thema}.
 */
@Service
@Transactional
public class ThemaServiceImpl implements ThemaService {

    private final Logger log = LoggerFactory.getLogger(ThemaServiceImpl.class);

    private final ThemaRepository themaRepository;

    private final ThemaMapper themaMapper;

    public ThemaServiceImpl(ThemaRepository themaRepository, ThemaMapper themaMapper) {
        this.themaRepository = themaRepository;
        this.themaMapper = themaMapper;
    }

    @Override
    public ThemaDTO save(ThemaDTO themaDTO) {
        log.debug("Request to save Thema : {}", themaDTO);
        Thema thema = themaMapper.toEntity(themaDTO);
        thema = themaRepository.save(thema);
        return themaMapper.toDto(thema);
    }

    @Override
    public Optional<ThemaDTO> partialUpdate(ThemaDTO themaDTO) {
        log.debug("Request to partially update Thema : {}", themaDTO);

        return themaRepository
            .findById(themaDTO.getId())
            .map(
                existingThema -> {
                    themaMapper.partialUpdate(existingThema, themaDTO);
                    return existingThema;
                }
            )
            .map(themaRepository::save)
            .map(themaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Themas");
        return themaRepository.findAll(pageable).map(themaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThemaDTO> findOne(Long id) {
        log.debug("Request to get Thema : {}", id);
        return themaRepository.findById(id).map(themaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Thema : {}", id);
        themaRepository.deleteById(id);
    }
}
