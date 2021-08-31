import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { maxLength, numeric, required } from 'vuelidate/lib/validators';

import { IRezetifizierung, Rezetifizierung } from '@/shared/model/rezetifizierung.model';
import RezetifizierungService from './rezetifizierung.service';

const validations: any = {
  rezetifizierung: {
    loginName: {
      maxLength: maxLength(100),
    },
    nachname: {
      maxLength: maxLength(100),
    },
    vorname: {
      maxLength: maxLength(100),
    },
    vermittlerNummer: {
      required,
      numeric,
    },
    rollenZugehoerigkeiten: {},
    dvcVertreterNummer: {
      maxLength: maxLength(100),
    },
    dvcBenutzerGruppe: {
      maxLength: maxLength(100),
    },
    icisSrGebaude: {
      maxLength: maxLength(100),
    },
    icisSrHaftpflicht: {
      maxLength: maxLength(100),
    },
    icisSrLeitungswasser: {
      maxLength: maxLength(100),
    },
    icisSrKfzKasko: {
      maxLength: maxLength(100),
    },
  },
};

@Component({
  validations,
})
export default class RezetifizierungUpdate extends mixins(JhiDataUtils) {
  @Inject('rezetifizierungService') private rezetifizierungService: () => RezetifizierungService;
  public rezetifizierung: IRezetifizierung = new Rezetifizierung();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rezetifizierungId) {
        vm.retrieveRezetifizierung(to.params.rezetifizierungId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.rezetifizierung.id) {
      this.rezetifizierungService()
        .update(this.rezetifizierung)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kpmgApp.rezetifizierung.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.rezetifizierungService()
        .create(this.rezetifizierung)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kpmgApp.rezetifizierung.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveRezetifizierung(rezetifizierungId): void {
    this.rezetifizierungService()
      .find(rezetifizierungId)
      .then(res => {
        this.rezetifizierung = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
