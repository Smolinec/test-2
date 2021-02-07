package cz.jirka.test.repository;

import cz.jirka.test.domain.PushNotificationToken;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PushNotificationToken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PushNotificationTokenRepository extends JpaRepository<PushNotificationToken, Long> {

    @Query("select pushNotificationToken from PushNotificationToken pushNotificationToken where pushNotificationToken.user.login = ?#{principal.username}")
    List<PushNotificationToken> findByUserIsCurrentUser();
}
