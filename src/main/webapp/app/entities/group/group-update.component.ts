import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import { IGroup, Group } from '@/shared/model/group.model';
import GroupService from './group.service';

const validations: any = {
  group: {
    name: {
      required,
      maxLength: maxLength(250),
    },
    description: {
      required,
      maxLength: maxLength(250),
    },
  },
};

@Component({
  validations,
})
export default class GroupUpdate extends Vue {
  @Inject('groupService') private groupService: () => GroupService;
  public group: IGroup = new Group();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.groupId) {
        vm.retrieveGroup(to.params.groupId);
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
    if (this.group.id) {
      this.groupService()
        .update(this.group)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('passwortverwaltungApp.group.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.groupService()
        .create(this.group)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('passwortverwaltungApp.group.created', { param: param.id });
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

  public retrieveGroup(groupId): void {
    this.groupService()
      .find(groupId)
      .then(res => {
        this.group = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
