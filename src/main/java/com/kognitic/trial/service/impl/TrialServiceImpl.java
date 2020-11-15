package com.kognitic.trial.service.impl;

import com.kognitic.trial.service.TrialService;
import com.kognitic.trial.domain.Trial;
import com.kognitic.trial.repository.TrialRepository;
import com.kognitic.trial.repository.search.TrialSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Trial}.
 */
@Service
@Transactional
public class TrialServiceImpl implements TrialService {

    private final Logger log = LoggerFactory.getLogger(TrialServiceImpl.class);

    private final TrialRepository trialRepository;

    private final TrialSearchRepository trialSearchRepository;

    public TrialServiceImpl(TrialRepository trialRepository, TrialSearchRepository trialSearchRepository) {
        this.trialRepository = trialRepository;
        this.trialSearchRepository = trialSearchRepository;
    }

    @Override
    public Trial save(Trial trial) {
        log.debug("Request to save Trial : {}", trial);
        Trial result = trialRepository.save(trial);
        trialSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Trial> findAll(Pageable pageable) {
        log.debug("Request to get all Trials");
        return trialRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Trial> findOne(Long id) {
        log.debug("Request to get Trial : {}", id);
        return trialRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trial : {}", id);
        trialRepository.deleteById(id);
        trialSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Trial> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Trials for query {}", query);
        return trialSearchRepository.search(queryStringQuery(query), pageable);    }
}
