package com.kognitic.trial.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A BiomarkerStrategy.
 */
@Entity
@Table(name = "biomarker_strategy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "biomarkerstrategy")
public class BiomarkerStrategy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "biomarker_strategy")
    private String biomarkerStrategy;

    @OneToMany(mappedBy = "biomarkerStrategy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BiomarkerMutation> biomarkerMutations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "biomarkerStrategies", allowSetters = true)
    private Biomarker biomarker;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiomarkerStrategy() {
        return biomarkerStrategy;
    }

    public BiomarkerStrategy biomarkerStrategy(String biomarkerStrategy) {
        this.biomarkerStrategy = biomarkerStrategy;
        return this;
    }

    public void setBiomarkerStrategy(String biomarkerStrategy) {
        this.biomarkerStrategy = biomarkerStrategy;
    }

    public Set<BiomarkerMutation> getBiomarkerMutations() {
        return biomarkerMutations;
    }

    public BiomarkerStrategy biomarkerMutations(Set<BiomarkerMutation> biomarkerMutations) {
        this.biomarkerMutations = biomarkerMutations;
        return this;
    }

    public BiomarkerStrategy addBiomarkerMutation(BiomarkerMutation biomarkerMutation) {
        this.biomarkerMutations.add(biomarkerMutation);
        biomarkerMutation.setBiomarkerStrategy(this);
        return this;
    }

    public BiomarkerStrategy removeBiomarkerMutation(BiomarkerMutation biomarkerMutation) {
        this.biomarkerMutations.remove(biomarkerMutation);
        biomarkerMutation.setBiomarkerStrategy(null);
        return this;
    }

    public void setBiomarkerMutations(Set<BiomarkerMutation> biomarkerMutations) {
        this.biomarkerMutations = biomarkerMutations;
    }

    public Biomarker getBiomarker() {
        return biomarker;
    }

    public BiomarkerStrategy biomarker(Biomarker biomarker) {
        this.biomarker = biomarker;
        return this;
    }

    public void setBiomarker(Biomarker biomarker) {
        this.biomarker = biomarker;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BiomarkerStrategy)) {
            return false;
        }
        return id != null && id.equals(((BiomarkerStrategy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BiomarkerStrategy{" +
            "id=" + getId() +
            ", biomarkerStrategy='" + getBiomarkerStrategy() + "'" +
            "}";
    }
}
