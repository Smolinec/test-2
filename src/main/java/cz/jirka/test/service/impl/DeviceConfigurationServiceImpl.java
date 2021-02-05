package cz.jirka.test.service.impl;

import cz.jirka.test.service.DeviceConfigurationService;
import cz.jirka.test.domain.DeviceConfiguration;
import cz.jirka.test.repository.DeviceConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DeviceConfiguration}.
 */
@Service
@Transactional
public class DeviceConfigurationServiceImpl implements DeviceConfigurationService {

    private final Logger log = LoggerFactory.getLogger(DeviceConfigurationServiceImpl.class);

    private final DeviceConfigurationRepository deviceConfigurationRepository;

    public DeviceConfigurationServiceImpl(DeviceConfigurationRepository deviceConfigurationRepository) {
        this.deviceConfigurationRepository = deviceConfigurationRepository;
    }

    @Override
    public DeviceConfiguration save(DeviceConfiguration deviceConfiguration) {
        log.debug("Request to save DeviceConfiguration : {}", deviceConfiguration);
        return deviceConfigurationRepository.save(deviceConfiguration);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceConfiguration> findAll() {
        log.debug("Request to get all DeviceConfigurations");
        return deviceConfigurationRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceConfiguration> findOne(Long id) {
        log.debug("Request to get DeviceConfiguration : {}", id);
        return deviceConfigurationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceConfiguration : {}", id);
        deviceConfigurationRepository.deleteById(id);
    }
}
