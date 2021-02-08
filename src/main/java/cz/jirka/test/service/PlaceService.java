package cz.jirka.test.service;

import cz.jirka.test.domain.Place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Place}.
 */
public interface PlaceService {

    /**
     * Save a place.
     *
     * @param place the entity to save.
     * @return the persisted entity.
     */
    Place save(Place place);

    /**
     * Get all the places.
     *
     * @return the list of entities.
     */
    List<Place> findAll();

    /**
     * Get all the places with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Place> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" place.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Place> findOne(Long id);

    /**
     * Delete the "id" place.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
