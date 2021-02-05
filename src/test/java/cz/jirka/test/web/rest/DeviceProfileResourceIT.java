package cz.jirka.test.web.rest;

import cz.jirka.test.TestMemoryH2App;
import cz.jirka.test.domain.DeviceProfile;
import cz.jirka.test.repository.DeviceProfileRepository;
import cz.jirka.test.service.DeviceProfileService;

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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DeviceProfileResource} REST controller.
 */
@SpringBootTest(classes = TestMemoryH2App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeviceProfileResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DeviceProfileRepository deviceProfileRepository;

    @Mock
    private DeviceProfileRepository deviceProfileRepositoryMock;

    @Mock
    private DeviceProfileService deviceProfileServiceMock;

    @Autowired
    private DeviceProfileService deviceProfileService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceProfileMockMvc;

    private DeviceProfile deviceProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceProfile createEntity(EntityManager em) {
        DeviceProfile deviceProfile = new DeviceProfile()
            .name(DEFAULT_NAME);
        return deviceProfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceProfile createUpdatedEntity(EntityManager em) {
        DeviceProfile deviceProfile = new DeviceProfile()
            .name(UPDATED_NAME);
        return deviceProfile;
    }

    @BeforeEach
    public void initTest() {
        deviceProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceProfile() throws Exception {
        int databaseSizeBeforeCreate = deviceProfileRepository.findAll().size();
        // Create the DeviceProfile
        restDeviceProfileMockMvc.perform(post("/api/device-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceProfile)))
            .andExpect(status().isCreated());

        // Validate the DeviceProfile in the database
        List<DeviceProfile> deviceProfileList = deviceProfileRepository.findAll();
        assertThat(deviceProfileList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceProfile testDeviceProfile = deviceProfileList.get(deviceProfileList.size() - 1);
        assertThat(testDeviceProfile.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDeviceProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceProfileRepository.findAll().size();

        // Create the DeviceProfile with an existing ID
        deviceProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceProfileMockMvc.perform(post("/api/device-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceProfile)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceProfile in the database
        List<DeviceProfile> deviceProfileList = deviceProfileRepository.findAll();
        assertThat(deviceProfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDeviceProfiles() throws Exception {
        // Initialize the database
        deviceProfileRepository.saveAndFlush(deviceProfile);

        // Get all the deviceProfileList
        restDeviceProfileMockMvc.perform(get("/api/device-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDeviceProfilesWithEagerRelationshipsIsEnabled() throws Exception {
        when(deviceProfileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeviceProfileMockMvc.perform(get("/api/device-profiles?eagerload=true"))
            .andExpect(status().isOk());

        verify(deviceProfileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDeviceProfilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(deviceProfileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeviceProfileMockMvc.perform(get("/api/device-profiles?eagerload=true"))
            .andExpect(status().isOk());

        verify(deviceProfileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDeviceProfile() throws Exception {
        // Initialize the database
        deviceProfileRepository.saveAndFlush(deviceProfile);

        // Get the deviceProfile
        restDeviceProfileMockMvc.perform(get("/api/device-profiles/{id}", deviceProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceProfile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDeviceProfile() throws Exception {
        // Get the deviceProfile
        restDeviceProfileMockMvc.perform(get("/api/device-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceProfile() throws Exception {
        // Initialize the database
        deviceProfileService.save(deviceProfile);

        int databaseSizeBeforeUpdate = deviceProfileRepository.findAll().size();

        // Update the deviceProfile
        DeviceProfile updatedDeviceProfile = deviceProfileRepository.findById(deviceProfile.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceProfile are not directly saved in db
        em.detach(updatedDeviceProfile);
        updatedDeviceProfile
            .name(UPDATED_NAME);

        restDeviceProfileMockMvc.perform(put("/api/device-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeviceProfile)))
            .andExpect(status().isOk());

        // Validate the DeviceProfile in the database
        List<DeviceProfile> deviceProfileList = deviceProfileRepository.findAll();
        assertThat(deviceProfileList).hasSize(databaseSizeBeforeUpdate);
        DeviceProfile testDeviceProfile = deviceProfileList.get(deviceProfileList.size() - 1);
        assertThat(testDeviceProfile.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceProfile() throws Exception {
        int databaseSizeBeforeUpdate = deviceProfileRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceProfileMockMvc.perform(put("/api/device-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceProfile)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceProfile in the database
        List<DeviceProfile> deviceProfileList = deviceProfileRepository.findAll();
        assertThat(deviceProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeviceProfile() throws Exception {
        // Initialize the database
        deviceProfileService.save(deviceProfile);

        int databaseSizeBeforeDelete = deviceProfileRepository.findAll().size();

        // Delete the deviceProfile
        restDeviceProfileMockMvc.perform(delete("/api/device-profiles/{id}", deviceProfile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceProfile> deviceProfileList = deviceProfileRepository.findAll();
        assertThat(deviceProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
