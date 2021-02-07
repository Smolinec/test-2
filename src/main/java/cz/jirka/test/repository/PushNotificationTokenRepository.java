package cz.jirka.test.repository;

import cz.jirka.test.domain.PushNotificationToken;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PushNotificationToken entity.
 */
@Repository
public interface PushNotificationTokenRepository extends JpaRepository<PushNotificationToken, Long> {

    @Query(value = "select distinct pushNotificationToken from PushNotificationToken pushNotificationToken left join fetch pushNotificationToken.users",
        countQuery = "select count(distinct pushNotificationToken) from PushNotificationToken pushNotificationToken")
    Page<PushNotificationToken> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct pushNotificationToken from PushNotificationToken pushNotificationToken left join fetch pushNotificationToken.users")
    List<PushNotificationToken> findAllWithEagerRelationships();

    @Query("select pushNotificationToken from PushNotificationToken pushNotificationToken left join fetch pushNotificationToken.users where pushNotificationToken.id =:id")
    Optional<PushNotificationToken> findOneWithEagerRelationships(@Param("id") Long id);
}
