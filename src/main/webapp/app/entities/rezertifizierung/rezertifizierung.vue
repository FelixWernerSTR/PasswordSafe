<template>
  <div>
    <h2 id="page-heading" data-cy="RezertifizierungHeading">
      <span v-text="$t('svisRezertifizierungApp.rezertifizierung.home.title')" id="rezertifizierung-heading">Rezertifizierungs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('svisRezertifizierungApp.rezertifizierung.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RezertifizierungCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rezertifizierung"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.home.createLabel')"> Create a new Rezertifizierung </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rezertifizierungs && rezertifizierungs.length === 0">
      <span v-text="$t('svisRezertifizierungApp.rezertifizierung.home.notFound')">No rezertifizierungs found</span>
    </div>
    <div class="table-responsive" v-if="rezertifizierungs && rezertifizierungs.length > 0">
      <table class="table table-striped" aria-describedby="rezertifizierungs">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('svisRezertifizierungApp.rezertifizierung.loginName')">Login Name</span></th>
            <th scope="row"><span v-text="$t('svisRezertifizierungApp.rezertifizierung.nachname')">Nachname</span></th>
            <th scope="row"><span v-text="$t('svisRezertifizierungApp.rezertifizierung.vorname')">Vorname</span></th>
            <th scope="row"><span v-text="$t('svisRezertifizierungApp.rezertifizierung.vermittlerNummer')">Vermittler Nummer</span></th>
            <th scope="row">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.rollenZugehoerigkeiten')">Rollen Zugehoerigkeiten</span>
            </th>
            <th scope="row">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.dvcVertreterNummer')">Dvc Vertreter Nummer</span>
            </th>
            <th scope="row"><span v-text="$t('svisRezertifizierungApp.rezertifizierung.dvcBenutzerGruppe')">Dvc Benutzer Gruppe</span></th>
            <th scope="row"><span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrGebaude')">Icis Sr Gebaude</span></th>
            <th scope="row"><span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrHaftpflicht')">Icis Sr Haftpflicht</span></th>
            <th scope="row">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrLeitungswasser')">Icis Sr Leitungswasser</span>
            </th>
            <th scope="row"><span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrKfzKasko')">Icis Sr Kfz Kasko</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rezertifizierung in rezertifizierungs" :key="rezertifizierung.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RezertifizierungView', params: { rezertifizierungId: rezertifizierung.id } }">{{
                rezertifizierung.id
              }}</router-link>
            </td>
            <td>{{ rezertifizierung.loginName }}</td>
            <td>{{ rezertifizierung.nachname }}</td>
            <td>{{ rezertifizierung.vorname }}</td>
            <td>{{ rezertifizierung.vermittlerNummer }}</td>
            <td>{{ rezertifizierung.rollenZugehoerigkeiten }}</td>
            <td>{{ rezertifizierung.dvcVertreterNummer }}</td>
            <td>{{ rezertifizierung.dvcBenutzerGruppe }}</td>
            <td>{{ rezertifizierung.icisSrGebaude }}</td>
            <td>{{ rezertifizierung.icisSrHaftpflicht }}</td>
            <td>{{ rezertifizierung.icisSrLeitungswasser }}</td>
            <td>{{ rezertifizierung.icisSrKfzKasko }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RezertifizierungView', params: { rezertifizierungId: rezertifizierung.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RezertifizierungEdit', params: { rezertifizierungId: rezertifizierung.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rezertifizierung)"
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
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="svisRezertifizierungApp.rezertifizierung.delete.question"
          data-cy="rezertifizierungDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p
          id="jhi-delete-rezertifizierung-heading"
          v-text="$t('svisRezertifizierungApp.rezertifizierung.delete.question', { id: removeId })"
        >
          Are you sure you want to delete this Rezertifizierung?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-rezertifizierung"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRezertifizierung()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./rezertifizierung.component.ts"></script>
