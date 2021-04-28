import { Component, Vue, Inject } from 'vue-property-decorator';

import { IThema } from '@/shared/model/thema.model';
import ThemaService from './thema.service';

@Component
export default class ThemaDetails extends Vue {
  @Inject('themaService') private themaService: () => ThemaService;
  public thema: IThema = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.themaId) {
        vm.retrieveThema(to.params.themaId);
      }
    });
  }

  public retrieveThema(themaId) {
    this.themaService()
      .find(themaId)
      .then(res => {
        this.thema = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
