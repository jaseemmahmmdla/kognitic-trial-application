package com.kognitic.trial.service.impl;

import com.kognitic.trial.service.IndicationService;
import com.kognitic.trial.domain.Indication;
import com.kognitic.trial.repository.IndicationRepository;
import com.kognitic.trial.repository.search.IndicationSearchRepository;
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
 * Service Implementation for managing {@link Indication}.
 */
@Service
@Transactional
public class IndicationServiceImpl implements IndicationService {

    private final Logger log = LoggerFactory.getLogger(IndicationServiceImpl.class);

    private final IndicationRepository indicationRepository;

    private final IndicationSearchRepository indicationSearchRepository;

    public IndicationServiceImpl(IndicationRepository indicationRepository, IndicationSearchRepository indicationSearchRepository) {
        this.indicationRepository = indicationRepository;
        this.indicationSearchRepository = indicationSearchRepository;
    }

    @Override
    public Indication save(Indication indication) {
        log.debug("Request to save Indication : {}", indication);
        Indication result = indicationRepository.save(indication);
        indicationSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Indication> findAll() {
        log.debug("Request to get all Indications");
        return indicationRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Indication> findOne(Long id) {
        log.debug("Request to get Indication : {}", id);
        return indicationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Indication : {}", id);
        indicationRepository.deleteById(id);
        indicationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Indication> search(String query) {
        log.debug("Request to search Indications for query {}", query);
        return StreamSupport
            .stream(indicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
