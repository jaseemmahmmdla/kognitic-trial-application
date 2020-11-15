package com.kognitic.trial.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.kognitic.trial.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.kognitic.trial.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.kognitic.trial.domain.User.class.getName());
            createCache(cm, com.kognitic.trial.domain.Authority.class.getName());
            createCache(cm, com.kognitic.trial.domain.User.class.getName() + ".authorities");
            createCache(cm, com.kognitic.trial.domain.Trial.class.getName());
            createCache(cm, com.kognitic.trial.domain.Trial.class.getName() + ".indications");
            createCache(cm, com.kognitic.trial.domain.Trial.class.getName() + ".biomarkers");
            createCache(cm, com.kognitic.trial.domain.Indication.class.getName());
            createCache(cm, com.kognitic.trial.domain.Indication.class.getName() + ".stages");
            createCache(cm, com.kognitic.trial.domain.Indication.class.getName() + ".indicationTypes");
            createCache(cm, com.kognitic.trial.domain.Indication.class.getName() + ".indicationBuckets");
            createCache(cm, com.kognitic.trial.domain.Indication.class.getName() + ".lineOfTherapies");
            createCache(cm, com.kognitic.trial.domain.IndicationType.class.getName());
            createCache(cm, com.kognitic.trial.domain.IndicationBucket.class.getName());
            createCache(cm, com.kognitic.trial.domain.Biomarker.class.getName());
            createCache(cm, com.kognitic.trial.domain.Biomarker.class.getName() + ".biomarkerStrategies");
            createCache(cm, com.kognitic.trial.domain.BiomarkerStrategy.class.getName());
            createCache(cm, com.kognitic.trial.domain.BiomarkerStrategy.class.getName() + ".biomarkerMutations");
            createCache(cm, com.kognitic.trial.domain.BiomarkerMutation.class.getName());
            createCache(cm, com.kognitic.trial.domain.LineOfTherapy.class.getName());
            createCache(cm, com.kognitic.trial.domain.Stage.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
