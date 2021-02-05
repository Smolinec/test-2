package cz.jirka.test.service.impl;

import cz.jirka.test.service.TemperatureValuesService;
import cz.jirka.test.domain.TemperatureValues;
import cz.jirka.test.repository.TemperatureValuesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TemperatureValues}.
 */
@Service
@Transactional
public class TemperatureValuesServiceImpl implements TemperatureValuesService {

    private final Logger log = LoggerFactory.getLogger(TemperatureValuesServiceImpl.class);

    private final TemperatureValuesRepository temperatureValuesRepository;

    public TemperatureValuesServiceImpl(TemperatureValuesRepository temperatureValuesRepository) {
        this.temperatureValuesRepository = temperatureValuesRepository;
    }

    @Override
    public TemperatureValues save(TemperatureValues temperatureValues) {
        log.debug("Request to save TemperatureValues : {}", temperatureValues);
        return temperatureValuesRepository.save(temperatureValues);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemperatureValues> findAll() {
        log.debug("Request to get all TemperatureValues");
        return temperatureValuesRepository.findAllWithEagerRelationships();
    }


    public Page<TemperatureValues> findAllWithEagerRelationships(Pageable pageable) {
        return temperatureValuesRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TemperatureValues> findOne(Long id) {
        log.debug("Request to get TemperatureValues : {}", id);
        return temperatureValuesRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TemperatureValues : {}", id);
        temperatureValuesRepository.deleteById(id);
    }
}
