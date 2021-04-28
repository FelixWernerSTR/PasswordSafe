package de.fw.passwordsafe.service.impl;

import de.fw.passwordsafe.domain.Entry;
import de.fw.passwordsafe.repository.EntryRepository;
import de.fw.passwordsafe.service.EntryService;
import de.fw.passwordsafe.service.dto.EntryDTO;
import de.fw.passwordsafe.service.mapper.EntryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Entry}.
 */
@Service
@Transactional
public class EntryServiceImpl implements EntryService {

    private final Logger log = LoggerFactory.getLogger(EntryServiceImpl.class);

    private final EntryRepository entryRepository;

    private final EntryMapper entryMapper;

    public EntryServiceImpl(EntryRepository entryRepository, EntryMapper entryMapper) {
        this.entryRepository = entryRepository;
        this.entryMapper = entryMapper;
    }

    @Override
    public EntryDTO save(EntryDTO entryDTO) {
        log.debug("Request to save Entry : {}", entryDTO);
        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        return entryMapper.toDto(entry);
    }

    @Override
    public Optional<EntryDTO> partialUpdate(EntryDTO entryDTO) {
        log.debug("Request to partially update Entry : {}", entryDTO);

        return entryRepository
            .findById(entryDTO.getId())
            .map(
                existingEntry -> {
                    entryMapper.partialUpdate(existingEntry, entryDTO);
                    return existingEntry;
                }
            )
            .map(entryRepository::save)
            .map(entryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entries");
        return entryRepository.findAll(pageable).map(entryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EntryDTO> findOne(Long id) {
        log.debug("Request to get Entry : {}", id);
        return entryRepository.findById(id).map(entryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entry : {}", id);
        entryRepository.deleteById(id);
    }
}
