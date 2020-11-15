package com.kognitic.trial.service.impl;

import com.kognitic.trial.service.IndicationTypeService;
import com.kognitic.trial.domain.IndicationType;
import com.kognitic.trial.repository.IndicationTypeRepository;
import com.kognitic.trial.repository.search.IndicationTypeSearchRepository;
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
 * Service Implementation for managing {@link IndicationType}.
 */
@Service
@Transactional
public class IndicationTypeServiceImpl implements IndicationTypeService {

    private final Logger log = LoggerFactory.getLogger(IndicationTypeServiceImpl.class);

    private final IndicationTypeRepository indicationTypeRepository;

    private final IndicationTypeSearchRepository indicationTypeSearchRepository;

    public IndicationTypeServiceImpl(IndicationTypeRepository indicationTypeRepository, IndicationTypeSearchRepository indicationTypeSearchRepository) {
        this.indicationTypeRepository = indicationTypeRepository;
        this.indicationTypeSearchRepository = indicationTypeSearchRepository;
    }

    @Override
    public IndicationType save(IndicationType indicationType) {
        log.debug("Request to save IndicationType : {}", indicationType);
        IndicationType result = indicationTypeRepository.save(indicationType);
        indicationTypeSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndicationType> findAll() {
        log.debug("Request to get all IndicationTypes");
        return indicationTypeRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<IndicationType> findOne(Long id) {
        log.debug("Request to get IndicationType : {}", id);
        return indicationTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndicationType : {}", id);
        indicationTypeRepository.deleteById(id);
        indicationTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndicationType> search(String query) {
        log.debug("Request to search IndicationTypes for query {}", query);
        return StreamSupport
            .stream(indicationTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
