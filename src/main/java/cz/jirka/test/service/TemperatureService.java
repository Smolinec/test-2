package cz.jirka.test.service;

import cz.jirka.test.domain.Temperature;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Temperature}.
 */
public interface TemperatureService {

    /**
     * Save a temperature.
     *
     * @param temperature the entity to save.
     * @return the persisted entity.
     */
    Temperature save(Temperature temperature);

    /**
     * Get all the temperatures.
     *
     * @return the list of entities.
     */
    List<Temperature> findAll();


    /**
     * Get the "id" temperature.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Temperature> findOne(Long id);

    /**
     * Delete the "id" temperature.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
