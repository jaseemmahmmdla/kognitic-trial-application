package com.kognitic.trial.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A BiomarkerMutation.
 */
@Entity
@Table(name = "biomarker_mutation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "biomarkermutation")
public class BiomarkerMutation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "biomarker_mutation")
    private String biomarkerMutation;

    @ManyToOne
    @JsonIgnoreProperties(value = "biomarkerMutations", allowSetters = true)
    private BiomarkerStrategy biomarkerStrategy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiomarkerMutation() {
        return biomarkerMutation;
    }

    public BiomarkerMutation biomarkerMutation(String biomarkerMutation) {
        this.biomarkerMutation = biomarkerMutation;
        return this;
    }

    public void setBiomarkerMutation(String biomarkerMutation) {
        this.biomarkerMutation = biomarkerMutation;
    }

    public BiomarkerStrategy getBiomarkerStrategy() {
        return biomarkerStrategy;
    }

    public BiomarkerMutation biomarkerStrategy(BiomarkerStrategy biomarkerStrategy) {
        this.biomarkerStrategy = biomarkerStrategy;
        return this;
    }

    public void setBiomarkerStrategy(BiomarkerStrategy biomarkerStrategy) {
        this.biomarkerStrategy = biomarkerStrategy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BiomarkerMutation)) {
            return false;
        }
        return id != null && id.equals(((BiomarkerMutation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BiomarkerMutation{" +
            "id=" + getId() +
            ", biomarkerMutation='" + getBiomarkerMutation() + "'" +
            "}";
    }
}
