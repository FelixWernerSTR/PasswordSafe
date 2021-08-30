import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IRezetifizierung } from '@/shared/model/rezetifizierung.model';
import RezetifizierungService from './rezetifizierung.service';

@Component
export default class RezetifizierungDetails extends mixins(JhiDataUtils) {
  @Inject('rezetifizierungService') private rezetifizierungService: () => RezetifizierungService;
  public rezetifizierung: IRezetifizierung = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rezetifizierungId) {
        vm.retrieveRezetifizierung(to.params.rezetifizierungId);
      }
    });
  }

  public retrieveRezetifizierung(rezetifizierungId) {
    this.rezetifizierungService()
      .find(rezetifizierungId)
      .then(res => {
        this.rezetifizierung = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
