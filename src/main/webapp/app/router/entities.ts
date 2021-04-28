import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Entry = () => import('@/entities/entry/entry.vue');
// prettier-ignore
const EntryUpdate = () => import('@/entities/entry/entry-update.vue');
// prettier-ignore
const EntryDetails = () => import('@/entities/entry/entry-details.vue');
// prettier-ignore
const Group = () => import('@/entities/group/group.vue');
// prettier-ignore
const GroupUpdate = () => import('@/entities/group/group-update.vue');
// prettier-ignore
const GroupDetails = () => import('@/entities/group/group-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/entry',
    name: 'Entry',
    component: Entry,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/entry/new',
    name: 'EntryCreate',
    component: EntryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/entry/:entryId/edit',
    name: 'EntryEdit',
    component: EntryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/entry/:entryId/view',
    name: 'EntryView',
    component: EntryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/group',
    name: 'Group',
    component: Group,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/group/new',
    name: 'GroupCreate',
    component: GroupUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/group/:groupId/edit',
    name: 'GroupEdit',
    component: GroupUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/group/:groupId/view',
    name: 'GroupView',
    component: GroupDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
