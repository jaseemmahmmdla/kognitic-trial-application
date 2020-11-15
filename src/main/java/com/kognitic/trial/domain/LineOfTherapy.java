package com.kognitic.trial.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A LineOfTherapy.
 */
@Entity
@Table(name = "line_of_therapy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "lineoftherapy")
public class LineOfTherapy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lot")
    private String lot;

    @ManyToOne
    @JsonIgnoreProperties(value = "lineOfTherapies", allowSetters = true)
    private Indication indication;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLot() {
        return lot;
    }

    public LineOfTherapy lot(String lot) {
        this.lot = lot;
        return this;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Indication getIndication() {
        return indication;
    }

    public LineOfTherapy indication(Indication indication) {
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
        if (!(o instanceof LineOfTherapy)) {
            return false;
        }
        return id != null && id.equals(((LineOfTherapy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LineOfTherapy{" +
            "id=" + getId() +
            ", lot='" + getLot() + "'" +
            "}";
    }
}
