package cz.jirka.test.web.rest;

import cz.jirka.test.domain.TemperatureValues;
import cz.jirka.test.service.TemperatureValuesService;
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
 * REST controller for managing {@link cz.jirka.test.domain.TemperatureValues}.
 */
@RestController
@RequestMapping("/api")
public class TemperatureValuesResource {

    private final Logger log = LoggerFactory.getLogger(TemperatureValuesResource.class);

    private static final String ENTITY_NAME = "temperatureValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemperatureValuesService temperatureValuesService;

    public TemperatureValuesResource(TemperatureValuesService temperatureValuesService) {
        this.temperatureValuesService = temperatureValuesService;
    }

    /**
     * {@code POST  /temperature-values} : Create a new temperatureValues.
     *
     * @param temperatureValues the temperatureValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new temperatureValues, or with status {@code 400 (Bad Request)} if the temperatureValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/temperature-values")
    public ResponseEntity<TemperatureValues> createTemperatureValues(@RequestBody TemperatureValues temperatureValues) throws URISyntaxException {
        log.debug("REST request to save TemperatureValues : {}", temperatureValues);
        if (temperatureValues.getId() != null) {
            throw new BadRequestAlertException("A new temperatureValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemperatureValues result = temperatureValuesService.save(temperatureValues);
        return ResponseEntity.created(new URI("/api/temperature-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /temperature-values} : Updates an existing temperatureValues.
     *
     * @param temperatureValues the temperatureValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated temperatureValues,
     * or with status {@code 400 (Bad Request)} if the temperatureValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the temperatureValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/temperature-values")
    public ResponseEntity<TemperatureValues> updateTemperatureValues(@RequestBody TemperatureValues temperatureValues) throws URISyntaxException {
        log.debug("REST request to update TemperatureValues : {}", temperatureValues);
        if (temperatureValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TemperatureValues result = temperatureValuesService.save(temperatureValues);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, temperatureValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /temperature-values} : get all the temperatureValues.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of temperatureValues in body.
     */
    @GetMapping("/temperature-values")
    public List<TemperatureValues> getAllTemperatureValues(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all TemperatureValues");
        return temperatureValuesService.findAll();
    }

    /**
     * {@code GET  /temperature-values/:id} : get the "id" temperatureValues.
     *
     * @param id the id of the temperatureValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the temperatureValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/temperature-values/{id}")
    public ResponseEntity<TemperatureValues> getTemperatureValues(@PathVariable Long id) {
        log.debug("REST request to get TemperatureValues : {}", id);
        Optional<TemperatureValues> temperatureValues = temperatureValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(temperatureValues);
    }

    /**
     * {@code DELETE  /temperature-values/:id} : delete the "id" temperatureValues.
     *
     * @param id the id of the temperatureValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/temperature-values/{id}")
    public ResponseEntity<Void> deleteTemperatureValues(@PathVariable Long id) {
        log.debug("REST request to delete TemperatureValues : {}", id);
        temperatureValuesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
