package cz.jirka.test.repository;

import cz.jirka.test.domain.TemperatureValues;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TemperatureValues entity.
 */
@Repository
public interface TemperatureValuesRepository extends JpaRepository<TemperatureValues, Long> {

    @Query(value = "select distinct temperatureValues from TemperatureValues temperatureValues left join fetch temperatureValues.temperatures",
        countQuery = "select count(distinct temperatureValues) from TemperatureValues temperatureValues")
    Page<TemperatureValues> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct temperatureValues from TemperatureValues temperatureValues left join fetch temperatureValues.temperatures")
    List<TemperatureValues> findAllWithEagerRelationships();

    @Query("select temperatureValues from TemperatureValues temperatureValues left join fetch temperatureValues.temperatures where temperatureValues.id =:id")
    Optional<TemperatureValues> findOneWithEagerRelationships(@Param("id") Long id);
}
