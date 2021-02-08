package cz.jirka.test.repository;

import cz.jirka.test.domain.PushNotificationToken;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PushNotificationToken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PushNotificationTokenRepository extends JpaRepository<PushNotificationToken, Long> {
}
