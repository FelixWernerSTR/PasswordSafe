import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Thementyp = () => import('@/entities/thementyp/thementyp.vue');
// prettier-ignore
const ThementypUpdate = () => import('@/entities/thementyp/thementyp-update.vue');
// prettier-ignore
const ThementypDetails = () => import('@/entities/thementyp/thementyp-details.vue');
// prettier-ignore
const Thema = () => import('@/entities/thema/thema.vue');
// prettier-ignore
const ThemaUpdate = () => import('@/entities/thema/thema-update.vue');
// prettier-ignore
const ThemaDetails = () => import('@/entities/thema/thema-details.vue');
// prettier-ignore
const Beitrag = () => import('@/entities/beitrag/beitrag.vue');
// prettier-ignore
const BeitragUpdate = () => import('@/entities/beitrag/beitrag-update.vue');
// prettier-ignore
const BeitragDetails = () => import('@/entities/beitrag/beitrag-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/thementyp',
    name: 'Thementyp',
    component: Thementyp,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/thementyp/new',
    name: 'ThementypCreate',
    component: ThementypUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/thementyp/:thementypId/edit',
    name: 'ThementypEdit',
    component: ThementypUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/thementyp/:thementypId/view',
    name: 'ThementypView',
    component: ThementypDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/thema',
    name: 'Thema',
    component: Thema,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/thema/new',
    name: 'ThemaCreate',
    component: ThemaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/thema/:themaId/edit',
    name: 'ThemaEdit',
    component: ThemaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/thema/:themaId/view',
    name: 'ThemaView',
    component: ThemaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/beitrag',
    name: 'Beitrag',
    component: Beitrag,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/beitrag/new',
    name: 'BeitragCreate',
    component: BeitragUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/beitrag/:beitragId/edit',
    name: 'BeitragEdit',
    component: BeitragUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/beitrag/:beitragId/view',
    name: 'BeitragView',
    component: BeitragDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
