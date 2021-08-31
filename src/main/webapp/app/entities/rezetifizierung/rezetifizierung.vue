<template>
  <div>
    <h2 id="page-heading" data-cy="RezetifizierungHeading">
      <span v-text="$t('svisRezetifizierungApp.rezetifizierung.home.title')" id="rezetifizierung-heading">Rezetifizierungs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('svisRezetifizierungApp.rezetifizierung.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RezetifizierungCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rezetifizierung"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('svisRezetifizierungApp.rezetifizierung.home.createLabel')"> Create a new Rezetifizierung </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rezetifizierungs && rezetifizierungs.length === 0">
      <span v-text="$t('svisRezetifizierungApp.rezetifizierung.home.notFound')">No rezetifizierungs found</span>
    </div>
    <div class="table-responsive" v-if="rezetifizierungs && rezetifizierungs.length > 0">
      <table class="table table-striped" aria-describedby="rezetifizierungs">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('loginName')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.loginName')">Login Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'loginName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nachname')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.nachname')">Nachname</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nachname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('vorname')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.vorname')">Vorname</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vorname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('vermittlerNummer')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.vermittlerNummer')">Vermittler Nummer</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vermittlerNummer'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rollenZugehoerigkeiten')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.rollenZugehoerigkeiten')">Rollen Zugehoerigkeiten</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rollenZugehoerigkeiten'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dvcVertreterNummer')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.dvcVertreterNummer')">Dvc Vertreter Nummer</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dvcVertreterNummer'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dvcBenutzerGruppe')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.dvcBenutzerGruppe')">Dvc Benutzer Gruppe</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dvcBenutzerGruppe'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icisSrGebaude')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrGebaude')">Icis Sr Gebaude</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icisSrGebaude'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icisSrHaftpflicht')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrHaftpflicht')">Icis Sr Haftpflicht</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icisSrHaftpflicht'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icisSrLeitungswasser')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrLeitungswasser')">Icis Sr Leitungswasser</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icisSrLeitungswasser'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icisSrKfzKasko')">
              <span v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrKfzKasko')">Icis Sr Kfz Kasko</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icisSrKfzKasko'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rezetifizierung in rezetifizierungs" :key="rezetifizierung.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RezetifizierungView', params: { rezetifizierungId: rezetifizierung.id } }">{{
                rezetifizierung.id
              }}</router-link>
            </td>
            <td>{{ rezetifizierung.loginName }}</td>
            <td>{{ rezetifizierung.nachname }}</td>
            <td>{{ rezetifizierung.vorname }}</td>
            <td>{{ rezetifizierung.vermittlerNummer }}</td>
            <td>{{ rezetifizierung.rollenZugehoerigkeiten }}</td>
            <td>{{ rezetifizierung.dvcVertreterNummer }}</td>
            <td>{{ rezetifizierung.dvcBenutzerGruppe }}</td>
            <td>{{ rezetifizierung.icisSrGebaude }}</td>
            <td>{{ rezetifizierung.icisSrHaftpflicht }}</td>
            <td>{{ rezetifizierung.icisSrLeitungswasser }}</td>
            <td>{{ rezetifizierung.icisSrKfzKasko }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RezetifizierungView', params: { rezetifizierungId: rezetifizierung.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RezetifizierungEdit', params: { rezetifizierungId: rezetifizierung.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rezetifizierung)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
        <infinite-loading
          ref="infiniteLoading"
          v-if="totalItems > itemsPerPage"
          :identifier="infiniteId"
          slot="append"
          @infinite="loadMore"
          force-use-infinite-wrapper=".el-table__body-wrapper"
          :distance="20"
        >
        </infinite-loading>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="svisRezetifizierungApp.rezetifizierung.delete.question"
          data-cy="rezetifizierungDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-rezetifizierung-heading" v-text="$t('svisRezetifizierungApp.rezetifizierung.delete.question', { id: removeId })">
          Are you sure you want to delete this Rezetifizierung?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-rezetifizierung"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRezetifizierung()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./rezetifizierung.component.ts"></script>
