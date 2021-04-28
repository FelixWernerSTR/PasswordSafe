import { Component, Vue, Inject } from 'vue-property-decorator';

import { maxLength, required } from 'vuelidate/lib/validators';

import GroupService from '@/entities/group/group.service';
import { IGroup } from '@/shared/model/group.model';

import { IEntry, Entry } from '@/shared/model/entry.model';
import EntryService from './entry.service';

const validations: any = {
  entry: {
    description: {
      maxLength: maxLength(250),
    },
    login: {
      required,
      maxLength: maxLength(250),
    },
    passwort: {
      required,
      maxLength: maxLength(250),
    },
    passwortReplay: {
      required,
      maxLength: maxLength(250),
    },
  },
};

@Component({
  validations,
})
export default class EntryUpdate extends Vue {
  @Inject('entryService') private entryService: () => EntryService;
  public entry: IEntry = new Entry();

  @Inject('groupService') private groupService: () => GroupService;

  public groups: IGroup[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.entryId) {
        vm.retrieveEntry(to.params.entryId);
      }
      vm.initRelationships();
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
    if (this.entry.id) {
      this.entryService()
        .update(this.entry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('passwortverwaltungApp.entry.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.entryService()
        .create(this.entry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('passwortverwaltungApp.entry.created', { param: param.id });
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

  public retrieveEntry(entryId): void {
    this.entryService()
      .find(entryId)
      .then(res => {
        this.entry = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.groupService()
      .retrieve()
      .then(res => {
        this.groups = res.data;
      });
  }
}
