package de.svi.svis5g.rezertifizierung.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import de.svi.svis5g.rezertifizierung.IntegrationTest;
import de.svi.svis5g.rezertifizierung.domain.Rezertifizierung;
import de.svi.svis5g.rezertifizierung.repository.RezertifizierungRepository;
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
 * Integration tests for the {@link RezertifizierungResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
@WithMockUser
class RezertifizierungResourceIT {

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

    private static final String ENTITY_API_URL = "/api/rezertifizierungs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RezertifizierungRepository rezertifizierungRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Rezertifizierung rezertifizierung;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rezertifizierung createEntity() {
        Rezertifizierung rezertifizierung = new Rezertifizierung()
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
        return rezertifizierung;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rezertifizierung createUpdatedEntity() {
        Rezertifizierung rezertifizierung = new Rezertifizierung()
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
        return rezertifizierung;
    }

    @BeforeEach
    public void setupCsrf() {
        webTestClient = webTestClient.mutateWith(csrf());
    }

    @BeforeEach
    public void initTest() {
        rezertifizierung = createEntity();
    }

    @Test
    void createRezertifizierung() throws Exception {
        int databaseSizeBeforeCreate = rezertifizierungRepository.findAll().collectList().block().size();
        // Create the Rezertifizierung
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeCreate + 1);
        Rezertifizierung testRezertifizierung = rezertifizierungList.get(rezertifizierungList.size() - 1);
        assertThat(testRezertifizierung.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
        assertThat(testRezertifizierung.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testRezertifizierung.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testRezertifizierung.getVermittlerNummer()).isEqualTo(DEFAULT_VERMITTLER_NUMMER);
        assertThat(testRezertifizierung.getRollenZugehoerigkeiten()).isEqualTo(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezertifizierung.getDvcVertreterNummer()).isEqualTo(DEFAULT_DVC_VERTRETER_NUMMER);
        assertThat(testRezertifizierung.getDvcBenutzerGruppe()).isEqualTo(DEFAULT_DVC_BENUTZER_GRUPPE);
        assertThat(testRezertifizierung.getIcisSrGebaude()).isEqualTo(DEFAULT_ICIS_SR_GEBAUDE);
        assertThat(testRezertifizierung.getIcisSrHaftpflicht()).isEqualTo(DEFAULT_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezertifizierung.getIcisSrLeitungswasser()).isEqualTo(DEFAULT_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezertifizierung.getIcisSrKfzKasko()).isEqualTo(DEFAULT_ICIS_SR_KFZ_KASKO);
    }

    @Test
    void createRezertifizierungWithExistingId() throws Exception {
        // Create the Rezertifizierung with an existing ID
        rezertifizierung.setId(1L);

        int databaseSizeBeforeCreate = rezertifizierungRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkVermittlerNummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = rezertifizierungRepository.findAll().collectList().block().size();
        // set the field null
        rezertifizierung.setVermittlerNummer(null);

        // Create the Rezertifizierung, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllRezertifizierungsAsStream() {
        // Initialize the database
        rezertifizierungRepository.save(rezertifizierung).block();

        List<Rezertifizierung> rezertifizierungList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Rezertifizierung.class)
            .getResponseBody()
            .filter(rezertifizierung::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(rezertifizierungList).isNotNull();
        assertThat(rezertifizierungList).hasSize(1);
        Rezertifizierung testRezertifizierung = rezertifizierungList.get(0);
        assertThat(testRezertifizierung.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
        assertThat(testRezertifizierung.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testRezertifizierung.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testRezertifizierung.getVermittlerNummer()).isEqualTo(DEFAULT_VERMITTLER_NUMMER);
        assertThat(testRezertifizierung.getRollenZugehoerigkeiten()).isEqualTo(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezertifizierung.getDvcVertreterNummer()).isEqualTo(DEFAULT_DVC_VERTRETER_NUMMER);
        assertThat(testRezertifizierung.getDvcBenutzerGruppe()).isEqualTo(DEFAULT_DVC_BENUTZER_GRUPPE);
        assertThat(testRezertifizierung.getIcisSrGebaude()).isEqualTo(DEFAULT_ICIS_SR_GEBAUDE);
        assertThat(testRezertifizierung.getIcisSrHaftpflicht()).isEqualTo(DEFAULT_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezertifizierung.getIcisSrLeitungswasser()).isEqualTo(DEFAULT_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezertifizierung.getIcisSrKfzKasko()).isEqualTo(DEFAULT_ICIS_SR_KFZ_KASKO);
    }

    @Test
    void getAllRezertifizierungs() {
        // Initialize the database
        rezertifizierungRepository.save(rezertifizierung).block();

        // Get all the rezertifizierungList
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
    void getRezertifizierung() {
        // Initialize the database
        rezertifizierungRepository.save(rezertifizierung).block();

        // Get the rezertifizierung
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, rezertifizierung.getId())
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
    void getNonExistingRezertifizierung() {
        // Get the rezertifizierung
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewRezertifizierung() throws Exception {
        // Initialize the database
        rezertifizierungRepository.save(rezertifizierung).block();

        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().collectList().block().size();

        // Update the rezertifizierung
        Rezertifizierung updatedRezertifizierung = rezertifizierungRepository.findById(rezertifizierung.getId()).block();
        updatedRezertifizierung
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
            .uri(ENTITY_API_URL_ID, updatedRezertifizierung.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedRezertifizierung))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
        Rezertifizierung testRezertifizierung = rezertifizierungList.get(rezertifizierungList.size() - 1);
        assertThat(testRezertifizierung.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
        assertThat(testRezertifizierung.getNachname()).isEqualTo(UPDATED_NACHNAME);
        assertThat(testRezertifizierung.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testRezertifizierung.getVermittlerNummer()).isEqualTo(UPDATED_VERMITTLER_NUMMER);
        assertThat(testRezertifizierung.getRollenZugehoerigkeiten()).isEqualTo(UPDATED_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezertifizierung.getDvcVertreterNummer()).isEqualTo(UPDATED_DVC_VERTRETER_NUMMER);
        assertThat(testRezertifizierung.getDvcBenutzerGruppe()).isEqualTo(UPDATED_DVC_BENUTZER_GRUPPE);
        assertThat(testRezertifizierung.getIcisSrGebaude()).isEqualTo(UPDATED_ICIS_SR_GEBAUDE);
        assertThat(testRezertifizierung.getIcisSrHaftpflicht()).isEqualTo(UPDATED_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezertifizierung.getIcisSrLeitungswasser()).isEqualTo(UPDATED_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezertifizierung.getIcisSrKfzKasko()).isEqualTo(UPDATED_ICIS_SR_KFZ_KASKO);
    }

    @Test
    void putNonExistingRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().collectList().block().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, rezertifizierung.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().collectList().block().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().collectList().block().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRezertifizierungWithPatch() throws Exception {
        // Initialize the database
        rezertifizierungRepository.save(rezertifizierung).block();

        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().collectList().block().size();

        // Update the rezertifizierung using partial update
        Rezertifizierung partialUpdatedRezertifizierung = new Rezertifizierung();
        partialUpdatedRezertifizierung.setId(rezertifizierung.getId());

        partialUpdatedRezertifizierung
            .vorname(UPDATED_VORNAME)
            .icisSrGebaude(UPDATED_ICIS_SR_GEBAUDE)
            .icisSrLeitungswasser(UPDATED_ICIS_SR_LEITUNGSWASSER)
            .icisSrKfzKasko(UPDATED_ICIS_SR_KFZ_KASKO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRezertifizierung.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRezertifizierung))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
        Rezertifizierung testRezertifizierung = rezertifizierungList.get(rezertifizierungList.size() - 1);
        assertThat(testRezertifizierung.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
        assertThat(testRezertifizierung.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testRezertifizierung.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testRezertifizierung.getVermittlerNummer()).isEqualTo(DEFAULT_VERMITTLER_NUMMER);
        assertThat(testRezertifizierung.getRollenZugehoerigkeiten()).isEqualTo(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezertifizierung.getDvcVertreterNummer()).isEqualTo(DEFAULT_DVC_VERTRETER_NUMMER);
        assertThat(testRezertifizierung.getDvcBenutzerGruppe()).isEqualTo(DEFAULT_DVC_BENUTZER_GRUPPE);
        assertThat(testRezertifizierung.getIcisSrGebaude()).isEqualTo(UPDATED_ICIS_SR_GEBAUDE);
        assertThat(testRezertifizierung.getIcisSrHaftpflicht()).isEqualTo(DEFAULT_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezertifizierung.getIcisSrLeitungswasser()).isEqualTo(UPDATED_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezertifizierung.getIcisSrKfzKasko()).isEqualTo(UPDATED_ICIS_SR_KFZ_KASKO);
    }

    @Test
    void fullUpdateRezertifizierungWithPatch() throws Exception {
        // Initialize the database
        rezertifizierungRepository.save(rezertifizierung).block();

        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().collectList().block().size();

        // Update the rezertifizierung using partial update
        Rezertifizierung partialUpdatedRezertifizierung = new Rezertifizierung();
        partialUpdatedRezertifizierung.setId(rezertifizierung.getId());

        partialUpdatedRezertifizierung
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
            .uri(ENTITY_API_URL_ID, partialUpdatedRezertifizierung.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRezertifizierung))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
        Rezertifizierung testRezertifizierung = rezertifizierungList.get(rezertifizierungList.size() - 1);
        assertThat(testRezertifizierung.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
        assertThat(testRezertifizierung.getNachname()).isEqualTo(UPDATED_NACHNAME);
        assertThat(testRezertifizierung.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testRezertifizierung.getVermittlerNummer()).isEqualTo(UPDATED_VERMITTLER_NUMMER);
        assertThat(testRezertifizierung.getRollenZugehoerigkeiten()).isEqualTo(UPDATED_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezertifizierung.getDvcVertreterNummer()).isEqualTo(UPDATED_DVC_VERTRETER_NUMMER);
        assertThat(testRezertifizierung.getDvcBenutzerGruppe()).isEqualTo(UPDATED_DVC_BENUTZER_GRUPPE);
        assertThat(testRezertifizierung.getIcisSrGebaude()).isEqualTo(UPDATED_ICIS_SR_GEBAUDE);
        assertThat(testRezertifizierung.getIcisSrHaftpflicht()).isEqualTo(UPDATED_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezertifizierung.getIcisSrLeitungswasser()).isEqualTo(UPDATED_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezertifizierung.getIcisSrKfzKasko()).isEqualTo(UPDATED_ICIS_SR_KFZ_KASKO);
    }

    @Test
    void patchNonExistingRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().collectList().block().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, rezertifizierung.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().collectList().block().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().collectList().block().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRezertifizierung() {
        // Initialize the database
        rezertifizierungRepository.save(rezertifizierung).block();

        int databaseSizeBeforeDelete = rezertifizierungRepository.findAll().collectList().block().size();

        // Delete the rezertifizierung
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, rezertifizierung.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll().collectList().block();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
