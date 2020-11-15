package com.kognitic.trial.service.impl;

import com.kognitic.trial.service.BiomarkerStrategyService;
import com.kognitic.trial.domain.BiomarkerStrategy;
import com.kognitic.trial.repository.BiomarkerStrategyRepository;
import com.kognitic.trial.repository.search.BiomarkerStrategySearchRepository;
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
 * Service Implementation for managing {@link BiomarkerStrategy}.
 */
@Service
@Transactional
public class BiomarkerStrategyServiceImpl implements BiomarkerStrategyService {

    private final Logger log = LoggerFactory.getLogger(BiomarkerStrategyServiceImpl.class);

    private final BiomarkerStrategyRepository biomarkerStrategyRepository;

    private final BiomarkerStrategySearchRepository biomarkerStrategySearchRepository;

    public BiomarkerStrategyServiceImpl(BiomarkerStrategyRepository biomarkerStrategyRepository, BiomarkerStrategySearchRepository biomarkerStrategySearchRepository) {
        this.biomarkerStrategyRepository = biomarkerStrategyRepository;
        this.biomarkerStrategySearchRepository = biomarkerStrategySearchRepository;
    }

    @Override
    public BiomarkerStrategy save(BiomarkerStrategy biomarkerStrategy) {
        log.debug("Request to save BiomarkerStrategy : {}", biomarkerStrategy);
        BiomarkerStrategy result = biomarkerStrategyRepository.save(biomarkerStrategy);
        biomarkerStrategySearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BiomarkerStrategy> findAll() {
        log.debug("Request to get all BiomarkerStrategies");
        return biomarkerStrategyRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BiomarkerStrategy> findOne(Long id) {
        log.debug("Request to get BiomarkerStrategy : {}", id);
        return biomarkerStrategyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BiomarkerStrategy : {}", id);
        biomarkerStrategyRepository.deleteById(id);
        biomarkerStrategySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BiomarkerStrategy> search(String query) {
        log.debug("Request to search BiomarkerStrategies for query {}", query);
        return StreamSupport
            .stream(biomarkerStrategySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
