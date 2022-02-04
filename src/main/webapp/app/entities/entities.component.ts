import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import EntryService from './entry/entry.service';
import GroupService from './group/group.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('entryService') private entryService = () => new EntryService();
  @Provide('groupService') private groupService = () => new GroupService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
