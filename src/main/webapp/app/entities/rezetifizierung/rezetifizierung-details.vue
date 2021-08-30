<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rezetifizierung">
        <h2 class="jh-entity-heading" data-cy="rezetifizierungDetailsHeading">
          <span v-text="$t('svisRezetifizierungApp.rezetifizierung.detail.title')">Rezetifizierung</span> {{ rezetifizierung.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.loginName')">Login Name</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.loginName }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.nachname')">Nachname</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.nachname }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.vorname')">Vorname</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.vorname }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.vermittlerNummer')">Vermittler Nummer</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.vermittlerNummer }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.rollenZugehoerigkeiten')">Rollen Zugehoerigkeiten</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.rollenZugehoerigkeiten }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.dvcVertreterNummer')">Dvc Vertreter Nummer</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.dvcVertreterNummer }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.dvcBenutzerGruppe')">Dvc Benutzer Gruppe</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.dvcBenutzerGruppe }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrGebaude')">Icis Sr Gebaude</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.icisSrGebaude }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrHaftpflicht')">Icis Sr Haftpflicht</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.icisSrHaftpflicht }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrLeitungswasser')">Icis Sr Leitungswasser</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.icisSrLeitungswasser }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrKfzKasko')">Icis Sr Kfz Kasko</span>
          </dt>
          <dd>
            <span>{{ rezetifizierung.icisSrKfzKasko }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="rezetifizierung.id"
          :to="{ name: 'RezetifizierungEdit', params: { rezetifizierungId: rezetifizierung.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./rezetifizierung-details.component.ts"></script>
