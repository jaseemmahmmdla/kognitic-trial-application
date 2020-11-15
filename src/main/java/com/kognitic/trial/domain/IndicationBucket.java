package com.kognitic.trial.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A IndicationBucket.
 */
@Entity
@Table(name = "indication_bucket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "indicationbucket")
public class IndicationBucket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indication_bucket")
    private String indicationBucket;

    @ManyToOne
    @JsonIgnoreProperties(value = "indicationBuckets", allowSetters = true)
    private Indication indication;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicationBucket() {
        return indicationBucket;
    }

    public IndicationBucket indicationBucket(String indicationBucket) {
        this.indicationBucket = indicationBucket;
        return this;
    }

    public void setIndicationBucket(String indicationBucket) {
        this.indicationBucket = indicationBucket;
    }

    public Indication getIndication() {
        return indication;
    }

    public IndicationBucket indication(Indication indication) {
        this.indication = indication;
        return this;
    }

    public void setIndication(Indication indication) {
        this.indication = indication;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndicationBucket)) {
            return false;
        }
        return id != null && id.equals(((IndicationBucket) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndicationBucket{" +
            "id=" + getId() +
            ", indicationBucket='" + getIndicationBucket() + "'" +
            "}";
    }
}
