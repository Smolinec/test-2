package cz.jirka.test.service;

import cz.jirka.test.domain.SMSNotification;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SMSNotification}.
 */
public interface SMSNotificationService {

    /**
     * Save a sMSNotification.
     *
     * @param sMSNotification the entity to save.
     * @return the persisted entity.
     */
    SMSNotification save(SMSNotification sMSNotification);

    /**
     * Get all the sMSNotifications.
     *
     * @return the list of entities.
     */
    List<SMSNotification> findAll();


    /**
     * Get the "id" sMSNotification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SMSNotification> findOne(Long id);

    /**
     * Delete the "id" sMSNotification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
