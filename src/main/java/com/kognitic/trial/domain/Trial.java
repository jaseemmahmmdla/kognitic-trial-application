package com.kognitic.trial.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Trial.
 */
@Entity
@Table(name = "trial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "trial")
public class Trial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trial_id")
    private String trialId;

    @Column(name = "trial_name")
    private String trialName;

    @OneToMany(mappedBy = "trial")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Indication> indications = new HashSet<>();

    @OneToMany(mappedBy = "trial")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Biomarker> biomarkers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrialId() {
        return trialId;
    }

    public Trial trialId(String trialId) {
        this.trialId = trialId;
        return this;
    }

    public void setTrialId(String trialId) {
        this.trialId = trialId;
    }

    public String getTrialName() {
        return trialName;
    }

    public Trial trialName(String trialName) {
        this.trialName = trialName;
        return this;
    }

    public void setTrialName(String trialName) {
        this.trialName = trialName;
    }

    public Set<Indication> getIndications() {
        return indications;
    }

    public Trial indications(Set<Indication> indications) {
        this.indications = indications;
        return this;
    }

    public Trial addIndication(Indication indication) {
        this.indications.add(indication);
        indication.setTrial(this);
        return this;
    }

    public Trial removeIndication(Indication indication) {
        this.indications.remove(indication);
        indication.setTrial(null);
        return this;
    }

    public void setIndications(Set<Indication> indications) {
        this.indications = indications;
    }

    public Set<Biomarker> getBiomarkers() {
        return biomarkers;
    }

    public Trial biomarkers(Set<Biomarker> biomarkers) {
        this.biomarkers = biomarkers;
        return this;
    }

    public Trial addBiomarker(Biomarker biomarker) {
        this.biomarkers.add(biomarker);
        biomarker.setTrial(this);
        return this;
    }

    public Trial removeBiomarker(Biomarker biomarker) {
        this.biomarkers.remove(biomarker);
        biomarker.setTrial(null);
        return this;
    }

    public void setBiomarkers(Set<Biomarker> biomarkers) {
        this.biomarkers = biomarkers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trial)) {
            return false;
        }
        return id != null && id.equals(((Trial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trial{" +
            "id=" + getId() +
            ", trialId='" + getTrialId() + "'" +
            ", trialName='" + getTrialName() + "'" +
            "}";
    }
}
