import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IBeitrag } from '@/shared/model/beitrag.model';
import BeitragService from './beitrag.service';

@Component
export default class BeitragDetails extends mixins(JhiDataUtils) {
  @Inject('beitragService') private beitragService: () => BeitragService;
  public beitrag: IBeitrag = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.beitragId) {
        vm.retrieveBeitrag(to.params.beitragId);
      }
    });
  }

  public retrieveBeitrag(beitragId) {
    this.beitragService()
      .find(beitragId)
      .then(res => {
        this.beitrag = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
