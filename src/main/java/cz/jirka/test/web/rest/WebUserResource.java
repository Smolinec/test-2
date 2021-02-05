package cz.jirka.test.web.rest;

import cz.jirka.test.domain.WebUser;
import cz.jirka.test.service.WebUserService;
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
 * REST controller for managing {@link cz.jirka.test.domain.WebUser}.
 */
@RestController
@RequestMapping("/api")
public class WebUserResource {

    private final Logger log = LoggerFactory.getLogger(WebUserResource.class);

    private static final String ENTITY_NAME = "webUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WebUserService webUserService;

    public WebUserResource(WebUserService webUserService) {
        this.webUserService = webUserService;
    }

    /**
     * {@code POST  /web-users} : Create a new webUser.
     *
     * @param webUser the webUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new webUser, or with status {@code 400 (Bad Request)} if the webUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/web-users")
    public ResponseEntity<WebUser> createWebUser(@RequestBody WebUser webUser) throws URISyntaxException {
        log.debug("REST request to save WebUser : {}", webUser);
        if (webUser.getId() != null) {
            throw new BadRequestAlertException("A new webUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WebUser result = webUserService.save(webUser);
        return ResponseEntity.created(new URI("/api/web-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /web-users} : Updates an existing webUser.
     *
     * @param webUser the webUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated webUser,
     * or with status {@code 400 (Bad Request)} if the webUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the webUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/web-users")
    public ResponseEntity<WebUser> updateWebUser(@RequestBody WebUser webUser) throws URISyntaxException {
        log.debug("REST request to update WebUser : {}", webUser);
        if (webUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WebUser result = webUserService.save(webUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, webUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /web-users} : get all the webUsers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of webUsers in body.
     */
    @GetMapping("/web-users")
    public List<WebUser> getAllWebUsers(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all WebUsers");
        return webUserService.findAll();
    }

    /**
     * {@code GET  /web-users/:id} : get the "id" webUser.
     *
     * @param id the id of the webUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the webUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/web-users/{id}")
    public ResponseEntity<WebUser> getWebUser(@PathVariable Long id) {
        log.debug("REST request to get WebUser : {}", id);
        Optional<WebUser> webUser = webUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(webUser);
    }

    /**
     * {@code DELETE  /web-users/:id} : delete the "id" webUser.
     *
     * @param id the id of the webUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/web-users/{id}")
    public ResponseEntity<Void> deleteWebUser(@PathVariable Long id) {
        log.debug("REST request to delete WebUser : {}", id);
        webUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
