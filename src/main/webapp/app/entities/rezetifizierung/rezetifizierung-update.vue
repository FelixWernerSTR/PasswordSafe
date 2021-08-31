<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="svisRezetifizierungApp.rezetifizierung.home.createOrEditLabel"
          data-cy="RezetifizierungCreateUpdateHeading"
          v-text="$t('svisRezetifizierungApp.rezetifizierung.home.createOrEditLabel')"
        >
          Create or edit a Rezetifizierung
        </h2>
        <div>
          <div class="form-group" v-if="rezetifizierung.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="rezetifizierung.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezetifizierungApp.rezetifizierung.loginName')"
              for="rezetifizierung-loginName"
              >Login Name</label
            >
            <input
              type="text"
              class="form-control"
              name="loginName"
              id="rezetifizierung-loginName"
              data-cy="loginName"
              :class="{ valid: !$v.rezetifizierung.loginName.$invalid, invalid: $v.rezetifizierung.loginName.$invalid }"
              v-model="$v.rezetifizierung.loginName.$model"
            />
            <div v-if="$v.rezetifizierung.loginName.$anyDirty && $v.rezetifizierung.loginName.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.loginName.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisRezetifizierungApp.rezetifizierung.nachname')" for="rezetifizierung-nachname"
              >Nachname</label
            >
            <input
              type="text"
              class="form-control"
              name="nachname"
              id="rezetifizierung-nachname"
              data-cy="nachname"
              :class="{ valid: !$v.rezetifizierung.nachname.$invalid, invalid: $v.rezetifizierung.nachname.$invalid }"
              v-model="$v.rezetifizierung.nachname.$model"
            />
            <div v-if="$v.rezetifizierung.nachname.$anyDirty && $v.rezetifizierung.nachname.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.nachname.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisRezetifizierungApp.rezetifizierung.vorname')" for="rezetifizierung-vorname"
              >Vorname</label
            >
            <input
              type="text"
              class="form-control"
              name="vorname"
              id="rezetifizierung-vorname"
              data-cy="vorname"
              :class="{ valid: !$v.rezetifizierung.vorname.$invalid, invalid: $v.rezetifizierung.vorname.$invalid }"
              v-model="$v.rezetifizierung.vorname.$model"
            />
            <div v-if="$v.rezetifizierung.vorname.$anyDirty && $v.rezetifizierung.vorname.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.vorname.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezetifizierungApp.rezetifizierung.vermittlerNummer')"
              for="rezetifizierung-vermittlerNummer"
              >Vermittler Nummer</label
            >
            <input
              type="number"
              class="form-control"
              name="vermittlerNummer"
              id="rezetifizierung-vermittlerNummer"
              data-cy="vermittlerNummer"
              :class="{ valid: !$v.rezetifizierung.vermittlerNummer.$invalid, invalid: $v.rezetifizierung.vermittlerNummer.$invalid }"
              v-model.number="$v.rezetifizierung.vermittlerNummer.$model"
              required
            />
            <div v-if="$v.rezetifizierung.vermittlerNummer.$anyDirty && $v.rezetifizierung.vermittlerNummer.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.vermittlerNummer.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.vermittlerNummer.numeric"
                v-text="$t('entity.validation.number')"
              >
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezetifizierungApp.rezetifizierung.rollenZugehoerigkeiten')"
              for="rezetifizierung-rollenZugehoerigkeiten"
              >Rollen Zugehoerigkeiten</label
            >
            <textarea
              class="form-control"
              name="rollenZugehoerigkeiten"
              id="rezetifizierung-rollenZugehoerigkeiten"
              data-cy="rollenZugehoerigkeiten"
              :class="{
                valid: !$v.rezetifizierung.rollenZugehoerigkeiten.$invalid,
                invalid: $v.rezetifizierung.rollenZugehoerigkeiten.$invalid,
              }"
              v-model="$v.rezetifizierung.rollenZugehoerigkeiten.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezetifizierungApp.rezetifizierung.dvcVertreterNummer')"
              for="rezetifizierung-dvcVertreterNummer"
              >Dvc Vertreter Nummer</label
            >
            <input
              type="text"
              class="form-control"
              name="dvcVertreterNummer"
              id="rezetifizierung-dvcVertreterNummer"
              data-cy="dvcVertreterNummer"
              :class="{ valid: !$v.rezetifizierung.dvcVertreterNummer.$invalid, invalid: $v.rezetifizierung.dvcVertreterNummer.$invalid }"
              v-model="$v.rezetifizierung.dvcVertreterNummer.$model"
            />
            <div v-if="$v.rezetifizierung.dvcVertreterNummer.$anyDirty && $v.rezetifizierung.dvcVertreterNummer.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.dvcVertreterNummer.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezetifizierungApp.rezetifizierung.dvcBenutzerGruppe')"
              for="rezetifizierung-dvcBenutzerGruppe"
              >Dvc Benutzer Gruppe</label
            >
            <input
              type="text"
              class="form-control"
              name="dvcBenutzerGruppe"
              id="rezetifizierung-dvcBenutzerGruppe"
              data-cy="dvcBenutzerGruppe"
              :class="{ valid: !$v.rezetifizierung.dvcBenutzerGruppe.$invalid, invalid: $v.rezetifizierung.dvcBenutzerGruppe.$invalid }"
              v-model="$v.rezetifizierung.dvcBenutzerGruppe.$model"
            />
            <div v-if="$v.rezetifizierung.dvcBenutzerGruppe.$anyDirty && $v.rezetifizierung.dvcBenutzerGruppe.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.dvcBenutzerGruppe.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrGebaude')"
              for="rezetifizierung-icisSrGebaude"
              >Icis Sr Gebaude</label
            >
            <input
              type="text"
              class="form-control"
              name="icisSrGebaude"
              id="rezetifizierung-icisSrGebaude"
              data-cy="icisSrGebaude"
              :class="{ valid: !$v.rezetifizierung.icisSrGebaude.$invalid, invalid: $v.rezetifizierung.icisSrGebaude.$invalid }"
              v-model="$v.rezetifizierung.icisSrGebaude.$model"
            />
            <div v-if="$v.rezetifizierung.icisSrGebaude.$anyDirty && $v.rezetifizierung.icisSrGebaude.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.icisSrGebaude.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrHaftpflicht')"
              for="rezetifizierung-icisSrHaftpflicht"
              >Icis Sr Haftpflicht</label
            >
            <input
              type="text"
              class="form-control"
              name="icisSrHaftpflicht"
              id="rezetifizierung-icisSrHaftpflicht"
              data-cy="icisSrHaftpflicht"
              :class="{ valid: !$v.rezetifizierung.icisSrHaftpflicht.$invalid, invalid: $v.rezetifizierung.icisSrHaftpflicht.$invalid }"
              v-model="$v.rezetifizierung.icisSrHaftpflicht.$model"
            />
            <div v-if="$v.rezetifizierung.icisSrHaftpflicht.$anyDirty && $v.rezetifizierung.icisSrHaftpflicht.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.icisSrHaftpflicht.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrLeitungswasser')"
              for="rezetifizierung-icisSrLeitungswasser"
              >Icis Sr Leitungswasser</label
            >
            <input
              type="text"
              class="form-control"
              name="icisSrLeitungswasser"
              id="rezetifizierung-icisSrLeitungswasser"
              data-cy="icisSrLeitungswasser"
              :class="{
                valid: !$v.rezetifizierung.icisSrLeitungswasser.$invalid,
                invalid: $v.rezetifizierung.icisSrLeitungswasser.$invalid,
              }"
              v-model="$v.rezetifizierung.icisSrLeitungswasser.$model"
            />
            <div v-if="$v.rezetifizierung.icisSrLeitungswasser.$anyDirty && $v.rezetifizierung.icisSrLeitungswasser.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.icisSrLeitungswasser.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezetifizierungApp.rezetifizierung.icisSrKfzKasko')"
              for="rezetifizierung-icisSrKfzKasko"
              >Icis Sr Kfz Kasko</label
            >
            <input
              type="text"
              class="form-control"
              name="icisSrKfzKasko"
              id="rezetifizierung-icisSrKfzKasko"
              data-cy="icisSrKfzKasko"
              :class="{ valid: !$v.rezetifizierung.icisSrKfzKasko.$invalid, invalid: $v.rezetifizierung.icisSrKfzKasko.$invalid }"
              v-model="$v.rezetifizierung.icisSrKfzKasko.$model"
            />
            <div v-if="$v.rezetifizierung.icisSrKfzKasko.$anyDirty && $v.rezetifizierung.icisSrKfzKasko.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezetifizierung.icisSrKfzKasko.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.rezetifizierung.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./rezetifizierung-update.component.ts"></script>
