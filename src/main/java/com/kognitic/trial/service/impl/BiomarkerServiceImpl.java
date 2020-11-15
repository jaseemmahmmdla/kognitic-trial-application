package com.kognitic.trial.service.impl;

import com.kognitic.trial.service.BiomarkerService;
import com.kognitic.trial.domain.Biomarker;
import com.kognitic.trial.repository.BiomarkerRepository;
import com.kognitic.trial.repository.search.BiomarkerSearchRepository;
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
 * Service Implementation for managing {@link Biomarker}.
 */
@Service
@Transactional
public class BiomarkerServiceImpl implements BiomarkerService {

    private final Logger log = LoggerFactory.getLogger(BiomarkerServiceImpl.class);

    private final BiomarkerRepository biomarkerRepository;

    private final BiomarkerSearchRepository biomarkerSearchRepository;

    public BiomarkerServiceImpl(BiomarkerRepository biomarkerRepository, BiomarkerSearchRepository biomarkerSearchRepository) {
        this.biomarkerRepository = biomarkerRepository;
        this.biomarkerSearchRepository = biomarkerSearchRepository;
    }

    @Override
    public Biomarker save(Biomarker biomarker) {
        log.debug("Request to save Biomarker : {}", biomarker);
        Biomarker result = biomarkerRepository.save(biomarker);
        biomarkerSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Biomarker> findAll() {
        log.debug("Request to get all Biomarkers");
        return biomarkerRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Biomarker> findOne(Long id) {
        log.debug("Request to get Biomarker : {}", id);
        return biomarkerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Biomarker : {}", id);
        biomarkerRepository.deleteById(id);
        biomarkerSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Biomarker> search(String query) {
        log.debug("Request to search Biomarkers for query {}", query);
        return StreamSupport
            .stream(biomarkerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
