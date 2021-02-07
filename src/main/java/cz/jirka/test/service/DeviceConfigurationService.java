package cz.jirka.test.service;

import cz.jirka.test.domain.DeviceConfiguration;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DeviceConfiguration}.
 */
public interface DeviceConfigurationService {

    /**
     * Save a deviceConfiguration.
     *
     * @param deviceConfiguration the entity to save.
     * @return the persisted entity.
     */
    DeviceConfiguration save(DeviceConfiguration deviceConfiguration);

    /**
     * Get all the deviceConfigurations.
     *
     * @return the list of entities.
     */
    List<DeviceConfiguration> findAll();


    /**
     * Get the "id" deviceConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceConfiguration> findOne(Long id);

    /**
     * Delete the "id" deviceConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
