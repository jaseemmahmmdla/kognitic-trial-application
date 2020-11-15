package com.kognitic.trial.service.impl;

import com.kognitic.trial.service.LineOfTherapyService;
import com.kognitic.trial.domain.LineOfTherapy;
import com.kognitic.trial.repository.LineOfTherapyRepository;
import com.kognitic.trial.repository.search.LineOfTherapySearchRepository;
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
 * Service Implementation for managing {@link LineOfTherapy}.
 */
@Service
@Transactional
public class LineOfTherapyServiceImpl implements LineOfTherapyService {

    private final Logger log = LoggerFactory.getLogger(LineOfTherapyServiceImpl.class);

    private final LineOfTherapyRepository lineOfTherapyRepository;

    private final LineOfTherapySearchRepository lineOfTherapySearchRepository;

    public LineOfTherapyServiceImpl(LineOfTherapyRepository lineOfTherapyRepository, LineOfTherapySearchRepository lineOfTherapySearchRepository) {
        this.lineOfTherapyRepository = lineOfTherapyRepository;
        this.lineOfTherapySearchRepository = lineOfTherapySearchRepository;
    }

    @Override
    public LineOfTherapy save(LineOfTherapy lineOfTherapy) {
        log.debug("Request to save LineOfTherapy : {}", lineOfTherapy);
        LineOfTherapy result = lineOfTherapyRepository.save(lineOfTherapy);
        lineOfTherapySearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineOfTherapy> findAll() {
        log.debug("Request to get all LineOfTherapies");
        return lineOfTherapyRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LineOfTherapy> findOne(Long id) {
        log.debug("Request to get LineOfTherapy : {}", id);
        return lineOfTherapyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LineOfTherapy : {}", id);
        lineOfTherapyRepository.deleteById(id);
        lineOfTherapySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineOfTherapy> search(String query) {
        log.debug("Request to search LineOfTherapies for query {}", query);
        return StreamSupport
            .stream(lineOfTherapySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
