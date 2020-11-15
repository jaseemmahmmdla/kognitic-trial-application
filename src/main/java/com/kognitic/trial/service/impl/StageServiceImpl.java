package com.kognitic.trial.service.impl;

import com.kognitic.trial.service.StageService;
import com.kognitic.trial.domain.Stage;
import com.kognitic.trial.repository.StageRepository;
import com.kognitic.trial.repository.search.StageSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Stage}.
 */
@Service
@Transactional
public class StageServiceImpl implements StageService {

    private final Logger log = LoggerFactory.getLogger(StageServiceImpl.class);

    private final StageRepository stageRepository;

    private final StageSearchRepository stageSearchRepository;

    public StageServiceImpl(StageRepository stageRepository, StageSearchRepository stageSearchRepository) {
        this.stageRepository = stageRepository;
        this.stageSearchRepository = stageSearchRepository;
    }

    @Override
    public Stage save(Stage stage) {
        log.debug("Request to save Stage : {}", stage);
        Stage result = stageRepository.save(stage);
        stageSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stage> findAll() {
        log.debug("Request to get all Stages");
        return stageRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Stage> findOne(Long id) {
        log.debug("Request to get Stage : {}", id);
        return stageRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stage : {}", id);
        stageRepository.deleteById(id);
        stageSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stage> search(String query) {
        log.debug("Request to search Stages for query {}", query);
        return StreamSupport
            .stream(stageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
