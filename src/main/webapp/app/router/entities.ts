import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Rezetifizierung = () => import('@/entities/rezetifizierung/rezetifizierung.vue');
// prettier-ignore
const RezetifizierungUpdate = () => import('@/entities/rezetifizierung/rezetifizierung-update.vue');
// prettier-ignore
const RezetifizierungDetails = () => import('@/entities/rezetifizierung/rezetifizierung-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/rezetifizierung',
    name: 'Rezetifizierung',
    component: Rezetifizierung,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/rezetifizierung/new',
    name: 'RezetifizierungCreate',
    component: RezetifizierungUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/rezetifizierung/:rezetifizierungId/edit',
    name: 'RezetifizierungEdit',
    component: RezetifizierungUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/rezetifizierung/:rezetifizierungId/view',
    name: 'RezetifizierungView',
    component: RezetifizierungDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
