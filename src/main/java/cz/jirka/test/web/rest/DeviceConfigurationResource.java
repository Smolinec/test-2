package cz.jirka.test.web.rest;

import cz.jirka.test.domain.DeviceConfiguration;
import cz.jirka.test.service.DeviceConfigurationService;
import cz.jirka.test.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link cz.jirka.test.domain.DeviceConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class DeviceConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(DeviceConfigurationResource.class);

    private static final String ENTITY_NAME = "deviceConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceConfigurationService deviceConfigurationService;

    public DeviceConfigurationResource(DeviceConfigurationService deviceConfigurationService) {
        this.deviceConfigurationService = deviceConfigurationService;
    }

    /**
     * {@code POST  /device-configurations} : Create a new deviceConfiguration.
     *
     * @param deviceConfiguration the deviceConfiguration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceConfiguration, or with status {@code 400 (Bad Request)} if the deviceConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-configurations")
    public ResponseEntity<DeviceConfiguration> createDeviceConfiguration(@RequestBody DeviceConfiguration deviceConfiguration) throws URISyntaxException {
        log.debug("REST request to save DeviceConfiguration : {}", deviceConfiguration);
        if (deviceConfiguration.getId() != null) {
            throw new BadRequestAlertException("A new deviceConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceConfiguration result = deviceConfigurationService.save(deviceConfiguration);
        return ResponseEntity.created(new URI("/api/device-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-configurations} : Updates an existing deviceConfiguration.
     *
     * @param deviceConfiguration the deviceConfiguration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceConfiguration,
     * or with status {@code 400 (Bad Request)} if the deviceConfiguration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceConfiguration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-configurations")
    public ResponseEntity<DeviceConfiguration> updateDeviceConfiguration(@RequestBody DeviceConfiguration deviceConfiguration) throws URISyntaxException {
        log.debug("REST request to update DeviceConfiguration : {}", deviceConfiguration);
        if (deviceConfiguration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceConfiguration result = deviceConfigurationService.save(deviceConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceConfiguration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-configurations} : get all the deviceConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceConfigurations in body.
     */
    @GetMapping("/device-configurations")
    public List<DeviceConfiguration> getAllDeviceConfigurations() {
        log.debug("REST request to get all DeviceConfigurations");
        return deviceConfigurationService.findAll();
    }

    /**
     * {@code GET  /device-configurations/:id} : get the "id" deviceConfiguration.
     *
     * @param id the id of the deviceConfiguration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceConfiguration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-configurations/{id}")
    public ResponseEntity<DeviceConfiguration> getDeviceConfiguration(@PathVariable Long id) {
        log.debug("REST request to get DeviceConfiguration : {}", id);
        Optional<DeviceConfiguration> deviceConfiguration = deviceConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceConfiguration);
    }

    /**
     * {@code DELETE  /device-configurations/:id} : delete the "id" deviceConfiguration.
     *
     * @param id the id of the deviceConfiguration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-configurations/{id}")
    public ResponseEntity<Void> deleteDeviceConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete DeviceConfiguration : {}", id);
        deviceConfigurationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
