package cz.jirka.test.web.rest;

import cz.jirka.test.TestMemoryH2App;
import cz.jirka.test.domain.PushNotificationToken;
import cz.jirka.test.repository.PushNotificationTokenRepository;
import cz.jirka.test.service.PushNotificationTokenService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static cz.jirka.test.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PushNotificationTokenResource} REST controller.
 */
@SpringBootTest(classes = TestMemoryH2App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PushNotificationTokenResourceIT {

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PushNotificationTokenRepository pushNotificationTokenRepository;

    @Mock
    private PushNotificationTokenRepository pushNotificationTokenRepositoryMock;

    @Mock
    private PushNotificationTokenService pushNotificationTokenServiceMock;

    @Autowired
    private PushNotificationTokenService pushNotificationTokenService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPushNotificationTokenMockMvc;

    private PushNotificationToken pushNotificationToken;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PushNotificationToken createEntity(EntityManager em) {
        PushNotificationToken pushNotificationToken = new PushNotificationToken()
            .token(DEFAULT_TOKEN)
            .timestamp(DEFAULT_TIMESTAMP);
        return pushNotificationToken;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PushNotificationToken createUpdatedEntity(EntityManager em) {
        PushNotificationToken pushNotificationToken = new PushNotificationToken()
            .token(UPDATED_TOKEN)
            .timestamp(UPDATED_TIMESTAMP);
        return pushNotificationToken;
    }

    @BeforeEach
    public void initTest() {
        pushNotificationToken = createEntity(em);
    }

    @Test
    @Transactional
    public void createPushNotificationToken() throws Exception {
        int databaseSizeBeforeCreate = pushNotificationTokenRepository.findAll().size();
        // Create the PushNotificationToken
        restPushNotificationTokenMockMvc.perform(post("/api/push-notification-tokens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pushNotificationToken)))
            .andExpect(status().isCreated());

        // Validate the PushNotificationToken in the database
        List<PushNotificationToken> pushNotificationTokenList = pushNotificationTokenRepository.findAll();
        assertThat(pushNotificationTokenList).hasSize(databaseSizeBeforeCreate + 1);
        PushNotificationToken testPushNotificationToken = pushNotificationTokenList.get(pushNotificationTokenList.size() - 1);
        assertThat(testPushNotificationToken.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testPushNotificationToken.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createPushNotificationTokenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pushNotificationTokenRepository.findAll().size();

        // Create the PushNotificationToken with an existing ID
        pushNotificationToken.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPushNotificationTokenMockMvc.perform(post("/api/push-notification-tokens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pushNotificationToken)))
            .andExpect(status().isBadRequest());

        // Validate the PushNotificationToken in the database
        List<PushNotificationToken> pushNotificationTokenList = pushNotificationTokenRepository.findAll();
        assertThat(pushNotificationTokenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPushNotificationTokens() throws Exception {
        // Initialize the database
        pushNotificationTokenRepository.saveAndFlush(pushNotificationToken);

        // Get all the pushNotificationTokenList
        restPushNotificationTokenMockMvc.perform(get("/api/push-notification-tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pushNotificationToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPushNotificationTokensWithEagerRelationshipsIsEnabled() throws Exception {
        when(pushNotificationTokenServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPushNotificationTokenMockMvc.perform(get("/api/push-notification-tokens?eagerload=true"))
            .andExpect(status().isOk());

        verify(pushNotificationTokenServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPushNotificationTokensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(pushNotificationTokenServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPushNotificationTokenMockMvc.perform(get("/api/push-notification-tokens?eagerload=true"))
            .andExpect(status().isOk());

        verify(pushNotificationTokenServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPushNotificationToken() throws Exception {
        // Initialize the database
        pushNotificationTokenRepository.saveAndFlush(pushNotificationToken);

        // Get the pushNotificationToken
        restPushNotificationTokenMockMvc.perform(get("/api/push-notification-tokens/{id}", pushNotificationToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pushNotificationToken.getId().intValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }
    @Test
    @Transactional
    public void getNonExistingPushNotificationToken() throws Exception {
        // Get the pushNotificationToken
        restPushNotificationTokenMockMvc.perform(get("/api/push-notification-tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePushNotificationToken() throws Exception {
        // Initialize the database
        pushNotificationTokenService.save(pushNotificationToken);

        int databaseSizeBeforeUpdate = pushNotificationTokenRepository.findAll().size();

        // Update the pushNotificationToken
        PushNotificationToken updatedPushNotificationToken = pushNotificationTokenRepository.findById(pushNotificationToken.getId()).get();
        // Disconnect from session so that the updates on updatedPushNotificationToken are not directly saved in db
        em.detach(updatedPushNotificationToken);
        updatedPushNotificationToken
            .token(UPDATED_TOKEN)
            .timestamp(UPDATED_TIMESTAMP);

        restPushNotificationTokenMockMvc.perform(put("/api/push-notification-tokens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPushNotificationToken)))
            .andExpect(status().isOk());

        // Validate the PushNotificationToken in the database
        List<PushNotificationToken> pushNotificationTokenList = pushNotificationTokenRepository.findAll();
        assertThat(pushNotificationTokenList).hasSize(databaseSizeBeforeUpdate);
        PushNotificationToken testPushNotificationToken = pushNotificationTokenList.get(pushNotificationTokenList.size() - 1);
        assertThat(testPushNotificationToken.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testPushNotificationToken.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingPushNotificationToken() throws Exception {
        int databaseSizeBeforeUpdate = pushNotificationTokenRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPushNotificationTokenMockMvc.perform(put("/api/push-notification-tokens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pushNotificationToken)))
            .andExpect(status().isBadRequest());

        // Validate the PushNotificationToken in the database
        List<PushNotificationToken> pushNotificationTokenList = pushNotificationTokenRepository.findAll();
        assertThat(pushNotificationTokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePushNotificationToken() throws Exception {
        // Initialize the database
        pushNotificationTokenService.save(pushNotificationToken);

        int databaseSizeBeforeDelete = pushNotificationTokenRepository.findAll().size();

        // Delete the pushNotificationToken
        restPushNotificationTokenMockMvc.perform(delete("/api/push-notification-tokens/{id}", pushNotificationToken.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PushNotificationToken> pushNotificationTokenList = pushNotificationTokenRepository.findAll();
        assertThat(pushNotificationTokenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
