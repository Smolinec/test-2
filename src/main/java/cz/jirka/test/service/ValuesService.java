package cz.jirka.test.service;

import cz.jirka.test.domain.Values;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Values}.
 */
public interface ValuesService {

    /**
     * Save a values.
     *
     * @param values the entity to save.
     * @return the persisted entity.
     */
    Values save(Values values);

    /**
     * Get all the values.
     *
     * @return the list of entities.
     */
    List<Values> findAll();

    /**
     * Get all the values with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Values> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" values.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Values> findOne(Long id);

    /**
     * Delete the "id" values.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
