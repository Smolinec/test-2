package cz.jirka.test.service;

import cz.jirka.test.domain.WebUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WebUser}.
 */
public interface WebUserService {

    /**
     * Save a webUser.
     *
     * @param webUser the entity to save.
     * @return the persisted entity.
     */
    WebUser save(WebUser webUser);

    /**
     * Get all the webUsers.
     *
     * @return the list of entities.
     */
    List<WebUser> findAll();

    /**
     * Get all the webUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<WebUser> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" webUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WebUser> findOne(Long id);

    /**
     * Delete the "id" webUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
