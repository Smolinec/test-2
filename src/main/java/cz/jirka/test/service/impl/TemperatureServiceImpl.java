package cz.jirka.test.service.impl;

import cz.jirka.test.service.TemperatureService;
import cz.jirka.test.domain.Temperature;
import cz.jirka.test.repository.TemperatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Temperature}.
 */
@Service
@Transactional
public class TemperatureServiceImpl implements TemperatureService {

    private final Logger log = LoggerFactory.getLogger(TemperatureServiceImpl.class);

    private final TemperatureRepository temperatureRepository;

    public TemperatureServiceImpl(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    @Override
    public Temperature save(Temperature temperature) {
        log.debug("Request to save Temperature : {}", temperature);
        return temperatureRepository.save(temperature);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Temperature> findAll() {
        log.debug("Request to get all Temperatures");
        return temperatureRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Temperature> findOne(Long id) {
        log.debug("Request to get Temperature : {}", id);
        return temperatureRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Temperature : {}", id);
        temperatureRepository.deleteById(id);
    }
}
