package cz.jirka.test.service;

import cz.jirka.test.domain.PushNotificationToken;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PushNotificationToken}.
 */
public interface PushNotificationTokenService {

    /**
     * Save a pushNotificationToken.
     *
     * @param pushNotificationToken the entity to save.
     * @return the persisted entity.
     */
    PushNotificationToken save(PushNotificationToken pushNotificationToken);

    /**
     * Get all the pushNotificationTokens.
     *
     * @return the list of entities.
     */
    List<PushNotificationToken> findAll();


    /**
     * Get the "id" pushNotificationToken.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PushNotificationToken> findOne(Long id);

    /**
     * Delete the "id" pushNotificationToken.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
