package cz.jirka.test.web.rest;

import cz.jirka.test.domain.Values;
import cz.jirka.test.service.ValuesService;
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
 * REST controller for managing {@link cz.jirka.test.domain.Values}.
 */
@RestController
@RequestMapping("/api")
public class ValuesResource {

    private final Logger log = LoggerFactory.getLogger(ValuesResource.class);

    private static final String ENTITY_NAME = "values";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValuesService valuesService;

    public ValuesResource(ValuesService valuesService) {
        this.valuesService = valuesService;
    }

    /**
     * {@code POST  /values} : Create a new values.
     *
     * @param values the values to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new values, or with status {@code 400 (Bad Request)} if the values has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/values")
    public ResponseEntity<Values> createValues(@RequestBody Values values) throws URISyntaxException {
        log.debug("REST request to save Values : {}", values);
        if (values.getId() != null) {
            throw new BadRequestAlertException("A new values cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Values result = valuesService.save(values);
        return ResponseEntity.created(new URI("/api/values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /values} : Updates an existing values.
     *
     * @param values the values to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated values,
     * or with status {@code 400 (Bad Request)} if the values is not valid,
     * or with status {@code 500 (Internal Server Error)} if the values couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/values")
    public ResponseEntity<Values> updateValues(@RequestBody Values values) throws URISyntaxException {
        log.debug("REST request to update Values : {}", values);
        if (values.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Values result = valuesService.save(values);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, values.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /values} : get all the values.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of values in body.
     */
    @GetMapping("/values")
    public List<Values> getAllValues(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Values");
        return valuesService.findAll();
    }

    /**
     * {@code GET  /values/:id} : get the "id" values.
     *
     * @param id the id of the values to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the values, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/values/{id}")
    public ResponseEntity<Values> getValues(@PathVariable Long id) {
        log.debug("REST request to get Values : {}", id);
        Optional<Values> values = valuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(values);
    }

    /**
     * {@code DELETE  /values/:id} : delete the "id" values.
     *
     * @param id the id of the values to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/values/{id}")
    public ResponseEntity<Void> deleteValues(@PathVariable Long id) {
        log.debug("REST request to delete Values : {}", id);
        valuesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
