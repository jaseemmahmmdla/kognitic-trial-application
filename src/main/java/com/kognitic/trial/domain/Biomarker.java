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
 * A Biomarker.
 */
@Entity
@Table(name = "biomarker")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "biomarker")
public class Biomarker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "biomarker")
    private String biomarker;

    @OneToMany(mappedBy = "biomarker")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BiomarkerStrategy> biomarkerStrategies = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "biomarkers", allowSetters = true)
    private Trial trial;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiomarker() {
        return biomarker;
    }

    public Biomarker biomarker(String biomarker) {
        this.biomarker = biomarker;
        return this;
    }

    public void setBiomarker(String biomarker) {
        this.biomarker = biomarker;
    }

    public Set<BiomarkerStrategy> getBiomarkerStrategies() {
        return biomarkerStrategies;
    }

    public Biomarker biomarkerStrategies(Set<BiomarkerStrategy> biomarkerStrategies) {
        this.biomarkerStrategies = biomarkerStrategies;
        return this;
    }

    public Biomarker addBiomarkerStrategy(BiomarkerStrategy biomarkerStrategy) {
        this.biomarkerStrategies.add(biomarkerStrategy);
        biomarkerStrategy.setBiomarker(this);
        return this;
    }

    public Biomarker removeBiomarkerStrategy(BiomarkerStrategy biomarkerStrategy) {
        this.biomarkerStrategies.remove(biomarkerStrategy);
        biomarkerStrategy.setBiomarker(null);
        return this;
    }

    public void setBiomarkerStrategies(Set<BiomarkerStrategy> biomarkerStrategies) {
        this.biomarkerStrategies = biomarkerStrategies;
    }

    public Trial getTrial() {
        return trial;
    }

    public Biomarker trial(Trial trial) {
        this.trial = trial;
        return this;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Biomarker)) {
            return false;
        }
        return id != null && id.equals(((Biomarker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Biomarker{" +
            "id=" + getId() +
            ", biomarker='" + getBiomarker() + "'" +
            "}";
    }
}
