import { Component, Provide, Vue } from 'vue-property-decorator';

import GroupService from './group/group.service';
import EntryService from './entry/entry.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('groupService') private groupService = () => new GroupService();
  @Provide('entryService') private entryService = () => new EntryService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
