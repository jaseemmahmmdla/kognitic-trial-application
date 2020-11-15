package com.kognitic.trial.service.impl;

import com.kognitic.trial.service.BiomarkerMutationService;
import com.kognitic.trial.domain.BiomarkerMutation;
import com.kognitic.trial.repository.BiomarkerMutationRepository;
import com.kognitic.trial.repository.search.BiomarkerMutationSearchRepository;
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
 * Service Implementation for managing {@link BiomarkerMutation}.
 */
@Service
@Transactional
public class BiomarkerMutationServiceImpl implements BiomarkerMutationService {

    private final Logger log = LoggerFactory.getLogger(BiomarkerMutationServiceImpl.class);

    private final BiomarkerMutationRepository biomarkerMutationRepository;

    private final BiomarkerMutationSearchRepository biomarkerMutationSearchRepository;

    public BiomarkerMutationServiceImpl(BiomarkerMutationRepository biomarkerMutationRepository, BiomarkerMutationSearchRepository biomarkerMutationSearchRepository) {
        this.biomarkerMutationRepository = biomarkerMutationRepository;
        this.biomarkerMutationSearchRepository = biomarkerMutationSearchRepository;
    }

    @Override
    public BiomarkerMutation save(BiomarkerMutation biomarkerMutation) {
        log.debug("Request to save BiomarkerMutation : {}", biomarkerMutation);
        BiomarkerMutation result = biomarkerMutationRepository.save(biomarkerMutation);
        biomarkerMutationSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BiomarkerMutation> findAll() {
        log.debug("Request to get all BiomarkerMutations");
        return biomarkerMutationRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BiomarkerMutation> findOne(Long id) {
        log.debug("Request to get BiomarkerMutation : {}", id);
        return biomarkerMutationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BiomarkerMutation : {}", id);
        biomarkerMutationRepository.deleteById(id);
        biomarkerMutationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BiomarkerMutation> search(String query) {
        log.debug("Request to search BiomarkerMutations for query {}", query);
        return StreamSupport
            .stream(biomarkerMutationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
