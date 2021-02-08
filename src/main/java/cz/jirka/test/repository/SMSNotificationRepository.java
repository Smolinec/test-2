package cz.jirka.test.repository;

import cz.jirka.test.domain.SMSNotification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SMSNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SMSNotificationRepository extends JpaRepository<SMSNotification, Long> {
}
