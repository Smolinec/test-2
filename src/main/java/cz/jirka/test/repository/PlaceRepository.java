package cz.jirka.test.repository;

import cz.jirka.test.domain.Place;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Place entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("select place from Place place where place.user.login = ?#{principal.username}")
    List<Place> findByUserIsCurrentUser();
}
