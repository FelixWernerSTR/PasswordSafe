package de.svi.svis5g.rezetifizierung.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.svi.svis5g.rezetifizierung.IntegrationTest;
import de.svi.svis5g.rezetifizierung.domain.Rezetifizierung;
import de.svi.svis5g.rezetifizierung.repository.RezetifizierungRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link RezetifizierungResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RezetifizierungResourceIT {

    private static final String DEFAULT_LOGIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NACHNAME = "AAAAAAAAAA";
    private static final String UPDATED_NACHNAME = "BBBBBBBBBB";

    private static final String DEFAULT_VORNAME = "AAAAAAAAAA";
    private static final String UPDATED_VORNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERMITTLER_NUMMER = 1;
    private static final Integer UPDATED_VERMITTLER_NUMMER = 2;

    private static final String DEFAULT_ROLLEN_ZUGEHOERIGKEITEN = "AAAAAAAAAA";
    private static final String UPDATED_ROLLEN_ZUGEHOERIGKEITEN = "BBBBBBBBBB";

    private static final String DEFAULT_DVC_VERTRETER_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_DVC_VERTRETER_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_DVC_BENUTZER_GRUPPE = "AAAAAAAAAA";
    private static final String UPDATED_DVC_BENUTZER_GRUPPE = "BBBBBBBBBB";

    private static final String DEFAULT_ICIS_SR_GEBAUDE = "AAAAAAAAAA";
    private static final String UPDATED_ICIS_SR_GEBAUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ICIS_SR_HAFTPFLICHT = "AAAAAAAAAA";
    private static final String UPDATED_ICIS_SR_HAFTPFLICHT = "BBBBBBBBBB";

    private static final String DEFAULT_ICIS_SR_LEITUNGSWASSER = "AAAAAAAAAA";
    private static final String UPDATED_ICIS_SR_LEITUNGSWASSER = "BBBBBBBBBB";

    private static final String DEFAULT_ICIS_SR_KFZ_KASKO = "AAAAAAAAAA";
    private static final String UPDATED_ICIS_SR_KFZ_KASKO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rezetifizierungs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RezetifizierungRepository rezetifizierungRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRezetifizierungMockMvc;

    private Rezetifizierung rezetifizierung;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rezetifizierung createEntity(EntityManager em) {
        Rezetifizierung rezetifizierung = new Rezetifizierung()
            .loginName(DEFAULT_LOGIN_NAME)
            .nachname(DEFAULT_NACHNAME)
            .vorname(DEFAULT_VORNAME)
            .vermittlerNummer(DEFAULT_VERMITTLER_NUMMER)
            .rollenZugehoerigkeiten(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN)
            .dvcVertreterNummer(DEFAULT_DVC_VERTRETER_NUMMER)
            .dvcBenutzerGruppe(DEFAULT_DVC_BENUTZER_GRUPPE)
            .icisSrGebaude(DEFAULT_ICIS_SR_GEBAUDE)
            .icisSrHaftpflicht(DEFAULT_ICIS_SR_HAFTPFLICHT)
            .icisSrLeitungswasser(DEFAULT_ICIS_SR_LEITUNGSWASSER)
            .icisSrKfzKasko(DEFAULT_ICIS_SR_KFZ_KASKO);
        return rezetifizierung;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rezetifizierung createUpdatedEntity(EntityManager em) {
        Rezetifizierung rezetifizierung = new Rezetifizierung()
            .loginName(UPDATED_LOGIN_NAME)
            .nachname(UPDATED_NACHNAME)
            .vorname(UPDATED_VORNAME)
            .vermittlerNummer(UPDATED_VERMITTLER_NUMMER)
            .rollenZugehoerigkeiten(UPDATED_ROLLEN_ZUGEHOERIGKEITEN)
            .dvcVertreterNummer(UPDATED_DVC_VERTRETER_NUMMER)
            .dvcBenutzerGruppe(UPDATED_DVC_BENUTZER_GRUPPE)
            .icisSrGebaude(UPDATED_ICIS_SR_GEBAUDE)
            .icisSrHaftpflicht(UPDATED_ICIS_SR_HAFTPFLICHT)
            .icisSrLeitungswasser(UPDATED_ICIS_SR_LEITUNGSWASSER)
            .icisSrKfzKasko(UPDATED_ICIS_SR_KFZ_KASKO);
        return rezetifizierung;
    }

    @BeforeEach
    public void initTest() {
        rezetifizierung = createEntity(em);
    }

    @Test
    @Transactional
    void createRezetifizierung() throws Exception {
        int databaseSizeBeforeCreate = rezetifizierungRepository.findAll().size();
        // Create the Rezetifizierung
        restRezetifizierungMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            )
            .andExpect(status().isCreated());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeCreate + 1);
        Rezetifizierung testRezetifizierung = rezetifizierungList.get(rezetifizierungList.size() - 1);
        assertThat(testRezetifizierung.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
        assertThat(testRezetifizierung.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testRezetifizierung.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testRezetifizierung.getVermittlerNummer()).isEqualTo(DEFAULT_VERMITTLER_NUMMER);
        assertThat(testRezetifizierung.getRollenZugehoerigkeiten()).isEqualTo(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezetifizierung.getDvcVertreterNummer()).isEqualTo(DEFAULT_DVC_VERTRETER_NUMMER);
        assertThat(testRezetifizierung.getDvcBenutzerGruppe()).isEqualTo(DEFAULT_DVC_BENUTZER_GRUPPE);
        assertThat(testRezetifizierung.getIcisSrGebaude()).isEqualTo(DEFAULT_ICIS_SR_GEBAUDE);
        assertThat(testRezetifizierung.getIcisSrHaftpflicht()).isEqualTo(DEFAULT_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezetifizierung.getIcisSrLeitungswasser()).isEqualTo(DEFAULT_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezetifizierung.getIcisSrKfzKasko()).isEqualTo(DEFAULT_ICIS_SR_KFZ_KASKO);
    }

    @Test
    @Transactional
    void createRezetifizierungWithExistingId() throws Exception {
        // Create the Rezetifizierung with an existing ID
        rezetifizierung.setId(1L);

        int databaseSizeBeforeCreate = rezetifizierungRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRezetifizierungMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVermittlerNummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = rezetifizierungRepository.findAll().size();
        // set the field null
        rezetifizierung.setVermittlerNummer(null);

        // Create the Rezetifizierung, which fails.

        restRezetifizierungMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            )
            .andExpect(status().isBadRequest());

        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRezetifizierungs() throws Exception {
        // Initialize the database
        rezetifizierungRepository.saveAndFlush(rezetifizierung);

        // Get all the rezetifizierungList
        restRezetifizierungMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rezetifizierung.getId().intValue())))
            .andExpect(jsonPath("$.[*].loginName").value(hasItem(DEFAULT_LOGIN_NAME)))
            .andExpect(jsonPath("$.[*].nachname").value(hasItem(DEFAULT_NACHNAME)))
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME)))
            .andExpect(jsonPath("$.[*].vermittlerNummer").value(hasItem(DEFAULT_VERMITTLER_NUMMER)))
            .andExpect(jsonPath("$.[*].rollenZugehoerigkeiten").value(hasItem(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN.toString())))
            .andExpect(jsonPath("$.[*].dvcVertreterNummer").value(hasItem(DEFAULT_DVC_VERTRETER_NUMMER)))
            .andExpect(jsonPath("$.[*].dvcBenutzerGruppe").value(hasItem(DEFAULT_DVC_BENUTZER_GRUPPE)))
            .andExpect(jsonPath("$.[*].icisSrGebaude").value(hasItem(DEFAULT_ICIS_SR_GEBAUDE)))
            .andExpect(jsonPath("$.[*].icisSrHaftpflicht").value(hasItem(DEFAULT_ICIS_SR_HAFTPFLICHT)))
            .andExpect(jsonPath("$.[*].icisSrLeitungswasser").value(hasItem(DEFAULT_ICIS_SR_LEITUNGSWASSER)))
            .andExpect(jsonPath("$.[*].icisSrKfzKasko").value(hasItem(DEFAULT_ICIS_SR_KFZ_KASKO)));
    }

    @Test
    @Transactional
    void getRezetifizierung() throws Exception {
        // Initialize the database
        rezetifizierungRepository.saveAndFlush(rezetifizierung);

        // Get the rezetifizierung
        restRezetifizierungMockMvc
            .perform(get(ENTITY_API_URL_ID, rezetifizierung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rezetifizierung.getId().intValue()))
            .andExpect(jsonPath("$.loginName").value(DEFAULT_LOGIN_NAME))
            .andExpect(jsonPath("$.nachname").value(DEFAULT_NACHNAME))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME))
            .andExpect(jsonPath("$.vermittlerNummer").value(DEFAULT_VERMITTLER_NUMMER))
            .andExpect(jsonPath("$.rollenZugehoerigkeiten").value(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN.toString()))
            .andExpect(jsonPath("$.dvcVertreterNummer").value(DEFAULT_DVC_VERTRETER_NUMMER))
            .andExpect(jsonPath("$.dvcBenutzerGruppe").value(DEFAULT_DVC_BENUTZER_GRUPPE))
            .andExpect(jsonPath("$.icisSrGebaude").value(DEFAULT_ICIS_SR_GEBAUDE))
            .andExpect(jsonPath("$.icisSrHaftpflicht").value(DEFAULT_ICIS_SR_HAFTPFLICHT))
            .andExpect(jsonPath("$.icisSrLeitungswasser").value(DEFAULT_ICIS_SR_LEITUNGSWASSER))
            .andExpect(jsonPath("$.icisSrKfzKasko").value(DEFAULT_ICIS_SR_KFZ_KASKO));
    }

    @Test
    @Transactional
    void getNonExistingRezetifizierung() throws Exception {
        // Get the rezetifizierung
        restRezetifizierungMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRezetifizierung() throws Exception {
        // Initialize the database
        rezetifizierungRepository.saveAndFlush(rezetifizierung);

        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().size();

        // Update the rezetifizierung
        Rezetifizierung updatedRezetifizierung = rezetifizierungRepository.findById(rezetifizierung.getId()).get();
        // Disconnect from session so that the updates on updatedRezetifizierung are not directly saved in db
        em.detach(updatedRezetifizierung);
        updatedRezetifizierung
            .loginName(UPDATED_LOGIN_NAME)
            .nachname(UPDATED_NACHNAME)
            .vorname(UPDATED_VORNAME)
            .vermittlerNummer(UPDATED_VERMITTLER_NUMMER)
            .rollenZugehoerigkeiten(UPDATED_ROLLEN_ZUGEHOERIGKEITEN)
            .dvcVertreterNummer(UPDATED_DVC_VERTRETER_NUMMER)
            .dvcBenutzerGruppe(UPDATED_DVC_BENUTZER_GRUPPE)
            .icisSrGebaude(UPDATED_ICIS_SR_GEBAUDE)
            .icisSrHaftpflicht(UPDATED_ICIS_SR_HAFTPFLICHT)
            .icisSrLeitungswasser(UPDATED_ICIS_SR_LEITUNGSWASSER)
            .icisSrKfzKasko(UPDATED_ICIS_SR_KFZ_KASKO);

        restRezetifizierungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRezetifizierung.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRezetifizierung))
            )
            .andExpect(status().isOk());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
        Rezetifizierung testRezetifizierung = rezetifizierungList.get(rezetifizierungList.size() - 1);
        assertThat(testRezetifizierung.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
        assertThat(testRezetifizierung.getNachname()).isEqualTo(UPDATED_NACHNAME);
        assertThat(testRezetifizierung.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testRezetifizierung.getVermittlerNummer()).isEqualTo(UPDATED_VERMITTLER_NUMMER);
        assertThat(testRezetifizierung.getRollenZugehoerigkeiten()).isEqualTo(UPDATED_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezetifizierung.getDvcVertreterNummer()).isEqualTo(UPDATED_DVC_VERTRETER_NUMMER);
        assertThat(testRezetifizierung.getDvcBenutzerGruppe()).isEqualTo(UPDATED_DVC_BENUTZER_GRUPPE);
        assertThat(testRezetifizierung.getIcisSrGebaude()).isEqualTo(UPDATED_ICIS_SR_GEBAUDE);
        assertThat(testRezetifizierung.getIcisSrHaftpflicht()).isEqualTo(UPDATED_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezetifizierung.getIcisSrLeitungswasser()).isEqualTo(UPDATED_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezetifizierung.getIcisSrKfzKasko()).isEqualTo(UPDATED_ICIS_SR_KFZ_KASKO);
    }

    @Test
    @Transactional
    void putNonExistingRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRezetifizierungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rezetifizierung.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRezetifizierungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRezetifizierungMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRezetifizierungWithPatch() throws Exception {
        // Initialize the database
        rezetifizierungRepository.saveAndFlush(rezetifizierung);

        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().size();

        // Update the rezetifizierung using partial update
        Rezetifizierung partialUpdatedRezetifizierung = new Rezetifizierung();
        partialUpdatedRezetifizierung.setId(rezetifizierung.getId());

        partialUpdatedRezetifizierung
            .vermittlerNummer(UPDATED_VERMITTLER_NUMMER)
            .rollenZugehoerigkeiten(UPDATED_ROLLEN_ZUGEHOERIGKEITEN)
            .dvcVertreterNummer(UPDATED_DVC_VERTRETER_NUMMER)
            .icisSrLeitungswasser(UPDATED_ICIS_SR_LEITUNGSWASSER);

        restRezetifizierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRezetifizierung.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRezetifizierung))
            )
            .andExpect(status().isOk());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
        Rezetifizierung testRezetifizierung = rezetifizierungList.get(rezetifizierungList.size() - 1);
        assertThat(testRezetifizierung.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
        assertThat(testRezetifizierung.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testRezetifizierung.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testRezetifizierung.getVermittlerNummer()).isEqualTo(UPDATED_VERMITTLER_NUMMER);
        assertThat(testRezetifizierung.getRollenZugehoerigkeiten()).isEqualTo(UPDATED_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezetifizierung.getDvcVertreterNummer()).isEqualTo(UPDATED_DVC_VERTRETER_NUMMER);
        assertThat(testRezetifizierung.getDvcBenutzerGruppe()).isEqualTo(DEFAULT_DVC_BENUTZER_GRUPPE);
        assertThat(testRezetifizierung.getIcisSrGebaude()).isEqualTo(DEFAULT_ICIS_SR_GEBAUDE);
        assertThat(testRezetifizierung.getIcisSrHaftpflicht()).isEqualTo(DEFAULT_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezetifizierung.getIcisSrLeitungswasser()).isEqualTo(UPDATED_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezetifizierung.getIcisSrKfzKasko()).isEqualTo(DEFAULT_ICIS_SR_KFZ_KASKO);
    }

    @Test
    @Transactional
    void fullUpdateRezetifizierungWithPatch() throws Exception {
        // Initialize the database
        rezetifizierungRepository.saveAndFlush(rezetifizierung);

        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().size();

        // Update the rezetifizierung using partial update
        Rezetifizierung partialUpdatedRezetifizierung = new Rezetifizierung();
        partialUpdatedRezetifizierung.setId(rezetifizierung.getId());

        partialUpdatedRezetifizierung
            .loginName(UPDATED_LOGIN_NAME)
            .nachname(UPDATED_NACHNAME)
            .vorname(UPDATED_VORNAME)
            .vermittlerNummer(UPDATED_VERMITTLER_NUMMER)
            .rollenZugehoerigkeiten(UPDATED_ROLLEN_ZUGEHOERIGKEITEN)
            .dvcVertreterNummer(UPDATED_DVC_VERTRETER_NUMMER)
            .dvcBenutzerGruppe(UPDATED_DVC_BENUTZER_GRUPPE)
            .icisSrGebaude(UPDATED_ICIS_SR_GEBAUDE)
            .icisSrHaftpflicht(UPDATED_ICIS_SR_HAFTPFLICHT)
            .icisSrLeitungswasser(UPDATED_ICIS_SR_LEITUNGSWASSER)
            .icisSrKfzKasko(UPDATED_ICIS_SR_KFZ_KASKO);

        restRezetifizierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRezetifizierung.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRezetifizierung))
            )
            .andExpect(status().isOk());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
        Rezetifizierung testRezetifizierung = rezetifizierungList.get(rezetifizierungList.size() - 1);
        assertThat(testRezetifizierung.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
        assertThat(testRezetifizierung.getNachname()).isEqualTo(UPDATED_NACHNAME);
        assertThat(testRezetifizierung.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testRezetifizierung.getVermittlerNummer()).isEqualTo(UPDATED_VERMITTLER_NUMMER);
        assertThat(testRezetifizierung.getRollenZugehoerigkeiten()).isEqualTo(UPDATED_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezetifizierung.getDvcVertreterNummer()).isEqualTo(UPDATED_DVC_VERTRETER_NUMMER);
        assertThat(testRezetifizierung.getDvcBenutzerGruppe()).isEqualTo(UPDATED_DVC_BENUTZER_GRUPPE);
        assertThat(testRezetifizierung.getIcisSrGebaude()).isEqualTo(UPDATED_ICIS_SR_GEBAUDE);
        assertThat(testRezetifizierung.getIcisSrHaftpflicht()).isEqualTo(UPDATED_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezetifizierung.getIcisSrLeitungswasser()).isEqualTo(UPDATED_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezetifizierung.getIcisSrKfzKasko()).isEqualTo(UPDATED_ICIS_SR_KFZ_KASKO);
    }

    @Test
    @Transactional
    void patchNonExistingRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRezetifizierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rezetifizierung.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRezetifizierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRezetifizierungMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRezetifizierung() throws Exception {
        // Initialize the database
        rezetifizierungRepository.saveAndFlush(rezetifizierung);

        int databaseSizeBeforeDelete = rezetifizierungRepository.findAll().size();

        // Delete the rezetifizierung
        restRezetifizierungMockMvc
            .perform(delete(ENTITY_API_URL_ID, rezetifizierung.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
