package de.svi.svis5g.rezetifizierung.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import de.svi.svis5g.rezetifizierung.IntegrationTest;
import de.svi.svis5g.rezetifizierung.domain.Rezetifizierung;
import de.svi.svis5g.rezetifizierung.repository.RezetifizierungRepository;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link RezetifizierungResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
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
    private WebTestClient webTestClient;

    private Rezetifizierung rezetifizierung;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rezetifizierung createEntity() {
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
    public static Rezetifizierung createUpdatedEntity() {
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
    public void setupCsrf() {
        webTestClient = webTestClient.mutateWith(csrf());
    }

    @BeforeEach
    public void initTest() {
        rezetifizierung = createEntity();
    }

    @Test
    void createRezetifizierung() throws Exception {
        int databaseSizeBeforeCreate = rezetifizierungRepository.findAll().collectList().block().size();
        // Create the Rezetifizierung
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
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
    void createRezetifizierungWithExistingId() throws Exception {
        // Create the Rezetifizierung with an existing ID
        rezetifizierung.setId(1L);

        int databaseSizeBeforeCreate = rezetifizierungRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkVermittlerNummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = rezetifizierungRepository.findAll().collectList().block().size();
        // set the field null
        rezetifizierung.setVermittlerNummer(null);

        // Create the Rezetifizierung, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllRezetifizierungsAsStream() {
        // Initialize the database
        rezetifizierungRepository.save(rezetifizierung).block();

        List<Rezetifizierung> rezetifizierungList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Rezetifizierung.class)
            .getResponseBody()
            .filter(rezetifizierung::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(rezetifizierungList).isNotNull();
        assertThat(rezetifizierungList).hasSize(1);
        Rezetifizierung testRezetifizierung = rezetifizierungList.get(0);
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
    void getAllRezetifizierungs() {
        // Initialize the database
        rezetifizierungRepository.save(rezetifizierung).block();

        // Get all the rezetifizierungList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].loginName")
            .value(hasItem(DEFAULT_LOGIN_NAME))
            .jsonPath("$.[*].nachname")
            .value(hasItem(DEFAULT_NACHNAME))
            .jsonPath("$.[*].vorname")
            .value(hasItem(DEFAULT_VORNAME))
            .jsonPath("$.[*].vermittlerNummer")
            .value(hasItem(DEFAULT_VERMITTLER_NUMMER))
            .jsonPath("$.[*].rollenZugehoerigkeiten")
            .value(hasItem(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN.toString()))
            .jsonPath("$.[*].dvcVertreterNummer")
            .value(hasItem(DEFAULT_DVC_VERTRETER_NUMMER))
            .jsonPath("$.[*].dvcBenutzerGruppe")
            .value(hasItem(DEFAULT_DVC_BENUTZER_GRUPPE))
            .jsonPath("$.[*].icisSrGebaude")
            .value(hasItem(DEFAULT_ICIS_SR_GEBAUDE))
            .jsonPath("$.[*].icisSrHaftpflicht")
            .value(hasItem(DEFAULT_ICIS_SR_HAFTPFLICHT))
            .jsonPath("$.[*].icisSrLeitungswasser")
            .value(hasItem(DEFAULT_ICIS_SR_LEITUNGSWASSER))
            .jsonPath("$.[*].icisSrKfzKasko")
            .value(hasItem(DEFAULT_ICIS_SR_KFZ_KASKO));
    }

    @Test
    void getRezetifizierung() {
        // Initialize the database
        rezetifizierungRepository.save(rezetifizierung).block();

        // Get the rezetifizierung
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, rezetifizierung.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.loginName")
            .value(is(DEFAULT_LOGIN_NAME))
            .jsonPath("$.nachname")
            .value(is(DEFAULT_NACHNAME))
            .jsonPath("$.vorname")
            .value(is(DEFAULT_VORNAME))
            .jsonPath("$.vermittlerNummer")
            .value(is(DEFAULT_VERMITTLER_NUMMER))
            .jsonPath("$.rollenZugehoerigkeiten")
            .value(is(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN.toString()))
            .jsonPath("$.dvcVertreterNummer")
            .value(is(DEFAULT_DVC_VERTRETER_NUMMER))
            .jsonPath("$.dvcBenutzerGruppe")
            .value(is(DEFAULT_DVC_BENUTZER_GRUPPE))
            .jsonPath("$.icisSrGebaude")
            .value(is(DEFAULT_ICIS_SR_GEBAUDE))
            .jsonPath("$.icisSrHaftpflicht")
            .value(is(DEFAULT_ICIS_SR_HAFTPFLICHT))
            .jsonPath("$.icisSrLeitungswasser")
            .value(is(DEFAULT_ICIS_SR_LEITUNGSWASSER))
            .jsonPath("$.icisSrKfzKasko")
            .value(is(DEFAULT_ICIS_SR_KFZ_KASKO));
    }

    @Test
    void getNonExistingRezetifizierung() {
        // Get the rezetifizierung
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewRezetifizierung() throws Exception {
        // Initialize the database
        rezetifizierungRepository.save(rezetifizierung).block();

        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().collectList().block().size();

        // Update the rezetifizierung
        Rezetifizierung updatedRezetifizierung = rezetifizierungRepository.findById(rezetifizierung.getId()).block();
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

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedRezetifizierung.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedRezetifizierung))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
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
    void putNonExistingRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().collectList().block().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, rezetifizierung.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().collectList().block().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().collectList().block().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRezetifizierungWithPatch() throws Exception {
        // Initialize the database
        rezetifizierungRepository.save(rezetifizierung).block();

        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().collectList().block().size();

        // Update the rezetifizierung using partial update
        Rezetifizierung partialUpdatedRezetifizierung = new Rezetifizierung();
        partialUpdatedRezetifizierung.setId(rezetifizierung.getId());

        partialUpdatedRezetifizierung
            .vermittlerNummer(UPDATED_VERMITTLER_NUMMER)
            .rollenZugehoerigkeiten(UPDATED_ROLLEN_ZUGEHOERIGKEITEN)
            .dvcVertreterNummer(UPDATED_DVC_VERTRETER_NUMMER)
            .icisSrLeitungswasser(UPDATED_ICIS_SR_LEITUNGSWASSER);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRezetifizierung.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRezetifizierung))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
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
    void fullUpdateRezetifizierungWithPatch() throws Exception {
        // Initialize the database
        rezetifizierungRepository.save(rezetifizierung).block();

        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().collectList().block().size();

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

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRezetifizierung.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRezetifizierung))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
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
    void patchNonExistingRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().collectList().block().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, rezetifizierung.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().collectList().block().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRezetifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezetifizierungRepository.findAll().collectList().block().size();
        rezetifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezetifizierung))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Rezetifizierung in the database
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRezetifizierung() {
        // Initialize the database
        rezetifizierungRepository.save(rezetifizierung).block();

        int databaseSizeBeforeDelete = rezetifizierungRepository.findAll().collectList().block().size();

        // Delete the rezetifizierung
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, rezetifizierung.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Rezetifizierung> rezetifizierungList = rezetifizierungRepository.findAll().collectList().block();
        assertThat(rezetifizierungList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
