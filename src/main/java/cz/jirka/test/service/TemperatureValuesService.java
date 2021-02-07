package cz.jirka.test.service;

import cz.jirka.test.domain.TemperatureValues;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TemperatureValues}.
 */
public interface TemperatureValuesService {

    /**
     * Save a temperatureValues.
     *
     * @param temperatureValues the entity to save.
     * @return the persisted entity.
     */
    TemperatureValues save(TemperatureValues temperatureValues);

    /**
     * Get all the temperatureValues.
     *
     * @return the list of entities.
     */
    List<TemperatureValues> findAll();

    /**
     * Get all the temperatureValues with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TemperatureValues> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" temperatureValues.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TemperatureValues> findOne(Long id);

    /**
     * Delete the "id" temperatureValues.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
