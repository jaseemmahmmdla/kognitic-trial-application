package com.kognitic.trial.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A IndicationType.
 */
@Entity
@Table(name = "indication_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "indicationtype")
public class IndicationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indication_type")
    private String indicationType;

    @ManyToOne
    @JsonIgnoreProperties(value = "indicationTypes", allowSetters = true)
    private Indication indication;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicationType() {
        return indicationType;
    }

    public IndicationType indicationType(String indicationType) {
        this.indicationType = indicationType;
        return this;
    }

    public void setIndicationType(String indicationType) {
        this.indicationType = indicationType;
    }

    public Indication getIndication() {
        return indication;
    }

    public IndicationType indication(Indication indication) {
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
        if (!(o instanceof IndicationType)) {
            return false;
        }
        return id != null && id.equals(((IndicationType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndicationType{" +
            "id=" + getId() +
            ", indicationType='" + getIndicationType() + "'" +
            "}";
    }
}
