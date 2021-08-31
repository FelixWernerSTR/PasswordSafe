import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRezertifizierung } from '@/shared/model/rezertifizierung.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import RezertifizierungService from './rezertifizierung.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Rezertifizierung extends mixins(JhiDataUtils) {
  @Inject('rezertifizierungService') private rezertifizierungService: () => RezertifizierungService;
  private removeId: number = null;

  public rezertifizierungs: IRezertifizierung[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRezertifizierungs();
  }

  public clear(): void {
    this.retrieveAllRezertifizierungs();
  }

  public retrieveAllRezertifizierungs(): void {
    this.isFetching = true;
    this.rezertifizierungService()
      .retrieve()
      .then(
        res => {
          this.rezertifizierungs = res.data;
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

  public prepareRemove(instance: IRezertifizierung): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRezertifizierung(): void {
    this.rezertifizierungService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('svisRezertifizierungApp.rezertifizierung.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRezertifizierungs();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
