package cz.jirka.test.web.rest;

import cz.jirka.test.domain.PushNotificationToken;
import cz.jirka.test.service.PushNotificationTokenService;
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
 * REST controller for managing {@link cz.jirka.test.domain.PushNotificationToken}.
 */
@RestController
@RequestMapping("/api")
public class PushNotificationTokenResource {

    private final Logger log = LoggerFactory.getLogger(PushNotificationTokenResource.class);

    private static final String ENTITY_NAME = "pushNotificationToken";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PushNotificationTokenService pushNotificationTokenService;

    public PushNotificationTokenResource(PushNotificationTokenService pushNotificationTokenService) {
        this.pushNotificationTokenService = pushNotificationTokenService;
    }

    /**
     * {@code POST  /push-notification-tokens} : Create a new pushNotificationToken.
     *
     * @param pushNotificationToken the pushNotificationToken to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pushNotificationToken, or with status {@code 400 (Bad Request)} if the pushNotificationToken has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/push-notification-tokens")
    public ResponseEntity<PushNotificationToken> createPushNotificationToken(@RequestBody PushNotificationToken pushNotificationToken) throws URISyntaxException {
        log.debug("REST request to save PushNotificationToken : {}", pushNotificationToken);
        if (pushNotificationToken.getId() != null) {
            throw new BadRequestAlertException("A new pushNotificationToken cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PushNotificationToken result = pushNotificationTokenService.save(pushNotificationToken);
        return ResponseEntity.created(new URI("/api/push-notification-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /push-notification-tokens} : Updates an existing pushNotificationToken.
     *
     * @param pushNotificationToken the pushNotificationToken to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pushNotificationToken,
     * or with status {@code 400 (Bad Request)} if the pushNotificationToken is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pushNotificationToken couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/push-notification-tokens")
    public ResponseEntity<PushNotificationToken> updatePushNotificationToken(@RequestBody PushNotificationToken pushNotificationToken) throws URISyntaxException {
        log.debug("REST request to update PushNotificationToken : {}", pushNotificationToken);
        if (pushNotificationToken.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PushNotificationToken result = pushNotificationTokenService.save(pushNotificationToken);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pushNotificationToken.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /push-notification-tokens} : get all the pushNotificationTokens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pushNotificationTokens in body.
     */
    @GetMapping("/push-notification-tokens")
    public List<PushNotificationToken> getAllPushNotificationTokens() {
        log.debug("REST request to get all PushNotificationTokens");
        return pushNotificationTokenService.findAll();
    }

    /**
     * {@code GET  /push-notification-tokens/:id} : get the "id" pushNotificationToken.
     *
     * @param id the id of the pushNotificationToken to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pushNotificationToken, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/push-notification-tokens/{id}")
    public ResponseEntity<PushNotificationToken> getPushNotificationToken(@PathVariable Long id) {
        log.debug("REST request to get PushNotificationToken : {}", id);
        Optional<PushNotificationToken> pushNotificationToken = pushNotificationTokenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pushNotificationToken);
    }

    /**
     * {@code DELETE  /push-notification-tokens/:id} : delete the "id" pushNotificationToken.
     *
     * @param id the id of the pushNotificationToken to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/push-notification-tokens/{id}")
    public ResponseEntity<Void> deletePushNotificationToken(@PathVariable Long id) {
        log.debug("REST request to delete PushNotificationToken : {}", id);
        pushNotificationTokenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
