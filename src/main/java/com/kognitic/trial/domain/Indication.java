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
 * A Indication.
 */
@Entity
@Table(name = "indication")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "indication")
public class Indication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indication")
    private String indication;

    @OneToMany(mappedBy = "indication")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Stage> stages = new HashSet<>();

    @OneToMany(mappedBy = "indication")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<IndicationType> indicationTypes = new HashSet<>();

    @OneToMany(mappedBy = "indication")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<IndicationBucket> indicationBuckets = new HashSet<>();

    @OneToMany(mappedBy = "indication")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LineOfTherapy> lineOfTherapies = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "indications", allowSetters = true)
    private Trial trial;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndication() {
        return indication;
    }

    public Indication indication(String indication) {
        this.indication = indication;
        return this;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public Set<Stage> getStages() {
        return stages;
    }

    public Indication stages(Set<Stage> stages) {
        this.stages = stages;
        return this;
    }

    public Indication addStage(Stage stage) {
        this.stages.add(stage);
        stage.setIndication(this);
        return this;
    }

    public Indication removeStage(Stage stage) {
        this.stages.remove(stage);
        stage.setIndication(null);
        return this;
    }

    public void setStages(Set<Stage> stages) {
        this.stages = stages;
    }

    public Set<IndicationType> getIndicationTypes() {
        return indicationTypes;
    }

    public Indication indicationTypes(Set<IndicationType> indicationTypes) {
        this.indicationTypes = indicationTypes;
        return this;
    }

    public Indication addIndicationType(IndicationType indicationType) {
        this.indicationTypes.add(indicationType);
        indicationType.setIndication(this);
        return this;
    }

    public Indication removeIndicationType(IndicationType indicationType) {
        this.indicationTypes.remove(indicationType);
        indicationType.setIndication(null);
        return this;
    }

    public void setIndicationTypes(Set<IndicationType> indicationTypes) {
        this.indicationTypes = indicationTypes;
    }

    public Set<IndicationBucket> getIndicationBuckets() {
        return indicationBuckets;
    }

    public Indication indicationBuckets(Set<IndicationBucket> indicationBuckets) {
        this.indicationBuckets = indicationBuckets;
        return this;
    }

    public Indication addIndicationBucket(IndicationBucket indicationBucket) {
        this.indicationBuckets.add(indicationBucket);
        indicationBucket.setIndication(this);
        return this;
    }

    public Indication removeIndicationBucket(IndicationBucket indicationBucket) {
        this.indicationBuckets.remove(indicationBucket);
        indicationBucket.setIndication(null);
        return this;
    }

    public void setIndicationBuckets(Set<IndicationBucket> indicationBuckets) {
        this.indicationBuckets = indicationBuckets;
    }

    public Set<LineOfTherapy> getLineOfTherapies() {
        return lineOfTherapies;
    }

    public Indication lineOfTherapies(Set<LineOfTherapy> lineOfTherapies) {
        this.lineOfTherapies = lineOfTherapies;
        return this;
    }

    public Indication addLineOfTherapy(LineOfTherapy lineOfTherapy) {
        this.lineOfTherapies.add(lineOfTherapy);
        lineOfTherapy.setIndication(this);
        return this;
    }

    public Indication removeLineOfTherapy(LineOfTherapy lineOfTherapy) {
        this.lineOfTherapies.remove(lineOfTherapy);
        lineOfTherapy.setIndication(null);
        return this;
    }

    public void setLineOfTherapies(Set<LineOfTherapy> lineOfTherapies) {
        this.lineOfTherapies = lineOfTherapies;
    }

    public Trial getTrial() {
        return trial;
    }

    public Indication trial(Trial trial) {
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
        if (!(o instanceof Indication)) {
            return false;
        }
        return id != null && id.equals(((Indication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Indication{" +
            "id=" + getId() +
            ", indication='" + getIndication() + "'" +
            "}";
    }
}
