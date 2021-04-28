package de.svi.svis5g.info.service.impl;

import de.svi.svis5g.info.domain.Beitrag;
import de.svi.svis5g.info.repository.BeitragRepository;
import de.svi.svis5g.info.service.BeitragService;
import de.svi.svis5g.info.service.dto.BeitragDTO;
import de.svi.svis5g.info.service.mapper.BeitragMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Beitrag}.
 */
@Service
@Transactional
public class BeitragServiceImpl implements BeitragService {

    private final Logger log = LoggerFactory.getLogger(BeitragServiceImpl.class);

    private final BeitragRepository beitragRepository;

    private final BeitragMapper beitragMapper;

    public BeitragServiceImpl(BeitragRepository beitragRepository, BeitragMapper beitragMapper) {
        this.beitragRepository = beitragRepository;
        this.beitragMapper = beitragMapper;
    }

    @Override
    public BeitragDTO save(BeitragDTO beitragDTO) {
        log.debug("Request to save Beitrag : {}", beitragDTO);
        Beitrag beitrag = beitragMapper.toEntity(beitragDTO);
        beitrag = beitragRepository.save(beitrag);
        return beitragMapper.toDto(beitrag);
    }

    @Override
    public Optional<BeitragDTO> partialUpdate(BeitragDTO beitragDTO) {
        log.debug("Request to partially update Beitrag : {}", beitragDTO);

        return beitragRepository
            .findById(beitragDTO.getId())
            .map(
                existingBeitrag -> {
                    beitragMapper.partialUpdate(existingBeitrag, beitragDTO);
                    return existingBeitrag;
                }
            )
            .map(beitragRepository::save)
            .map(beitragMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BeitragDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Beitrags");
        return beitragRepository.findAll(pageable).map(beitragMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeitragDTO> findOne(Long id) {
        log.debug("Request to get Beitrag : {}", id);
        return beitragRepository.findById(id).map(beitragMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Beitrag : {}", id);
        beitragRepository.deleteById(id);
    }
}
