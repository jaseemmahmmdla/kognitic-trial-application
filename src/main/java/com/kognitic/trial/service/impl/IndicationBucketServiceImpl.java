package com.kognitic.trial.service.impl;

import com.kognitic.trial.service.IndicationBucketService;
import com.kognitic.trial.domain.IndicationBucket;
import com.kognitic.trial.repository.IndicationBucketRepository;
import com.kognitic.trial.repository.search.IndicationBucketSearchRepository;
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
 * Service Implementation for managing {@link IndicationBucket}.
 */
@Service
@Transactional
public class IndicationBucketServiceImpl implements IndicationBucketService {

    private final Logger log = LoggerFactory.getLogger(IndicationBucketServiceImpl.class);

    private final IndicationBucketRepository indicationBucketRepository;

    private final IndicationBucketSearchRepository indicationBucketSearchRepository;

    public IndicationBucketServiceImpl(IndicationBucketRepository indicationBucketRepository, IndicationBucketSearchRepository indicationBucketSearchRepository) {
        this.indicationBucketRepository = indicationBucketRepository;
        this.indicationBucketSearchRepository = indicationBucketSearchRepository;
    }

    @Override
    public IndicationBucket save(IndicationBucket indicationBucket) {
        log.debug("Request to save IndicationBucket : {}", indicationBucket);
        IndicationBucket result = indicationBucketRepository.save(indicationBucket);
        indicationBucketSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndicationBucket> findAll() {
        log.debug("Request to get all IndicationBuckets");
        return indicationBucketRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<IndicationBucket> findOne(Long id) {
        log.debug("Request to get IndicationBucket : {}", id);
        return indicationBucketRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndicationBucket : {}", id);
        indicationBucketRepository.deleteById(id);
        indicationBucketSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndicationBucket> search(String query) {
        log.debug("Request to search IndicationBuckets for query {}", query);
        return StreamSupport
            .stream(indicationBucketSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
