package cz.jirka.test.service;

import cz.jirka.test.domain.DeviceProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DeviceProfile}.
 */
public interface DeviceProfileService {

    /**
     * Save a deviceProfile.
     *
     * @param deviceProfile the entity to save.
     * @return the persisted entity.
     */
    DeviceProfile save(DeviceProfile deviceProfile);

    /**
     * Get all the deviceProfiles.
     *
     * @return the list of entities.
     */
    List<DeviceProfile> findAll();

    /**
     * Get all the deviceProfiles with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<DeviceProfile> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" deviceProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceProfile> findOne(Long id);

    /**
     * Delete the "id" deviceProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
