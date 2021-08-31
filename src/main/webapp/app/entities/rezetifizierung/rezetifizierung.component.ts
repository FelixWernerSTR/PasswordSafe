import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRezetifizierung } from '@/shared/model/rezetifizierung.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import RezetifizierungService from './rezetifizierung.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Rezetifizierung extends mixins(JhiDataUtils) {
  @Inject('rezetifizierungService') private rezetifizierungService: () => RezetifizierungService;
  private removeId: number = null;

  public rezetifizierungs: IRezetifizierung[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRezetifizierungs();
  }

  public clear(): void {
    this.retrieveAllRezetifizierungs();
  }

  public retrieveAllRezetifizierungs(): void {
    this.isFetching = true;
    this.rezetifizierungService()
      .retrieve()
      .then(
        res => {
          this.rezetifizierungs = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IRezetifizierung): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRezetifizierung(): void {
    this.rezetifizierungService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kpmgApp.rezetifizierung.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRezetifizierungs();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
