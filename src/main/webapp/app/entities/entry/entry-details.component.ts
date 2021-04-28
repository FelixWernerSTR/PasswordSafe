import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEntry } from '@/shared/model/entry.model';
import EntryService from './entry.service';

@Component
export default class EntryDetails extends Vue {
  @Inject('entryService') private entryService: () => EntryService;
  public entry: IEntry = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.entryId) {
        vm.retrieveEntry(to.params.entryId);
      }
    });
  }

  public retrieveEntry(entryId) {
    this.entryService()
      .find(entryId)
      .then(res => {
        this.entry = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
