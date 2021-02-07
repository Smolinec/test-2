package cz.jirka.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A TemperatureValues.
 */
@Entity
@Table(name = "temperature_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TemperatureValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private Double value;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @ManyToOne
    @JsonIgnoreProperties(value = "temperatureValues", allowSetters = true)
    private Temperature temperature;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "temperature_values_temperature",
               joinColumns = @JoinColumn(name = "temperature_values_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "temperature_id", referencedColumnName = "id"))
    private Set<Temperature> temperatures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public TemperatureValues value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public TemperatureValues timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public TemperatureValues temperature(Temperature temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Set<Temperature> getTemperatures() {
        return temperatures;
    }

    public TemperatureValues temperatures(Set<Temperature> temperatures) {
        this.temperatures = temperatures;
        return this;
    }

    public TemperatureValues addTemperature(Temperature temperature) {
        this.temperatures.add(temperature);
        temperature.getTemperatureValues().add(this);
        return this;
    }

    public TemperatureValues removeTemperature(Temperature temperature) {
        this.temperatures.remove(temperature);
        temperature.getTemperatureValues().remove(this);
        return this;
    }

    public void setTemperatures(Set<Temperature> temperatures) {
        this.temperatures = temperatures;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemperatureValues)) {
            return false;
        }
        return id != null && id.equals(((TemperatureValues) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemperatureValues{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
