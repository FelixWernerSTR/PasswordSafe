import { Authority } from '@/shared/security/authority';

const SvisDocsComponent = () => import('@/admin/docs/docs.vue');
const SvisConfigurationComponent = () => import('@/admin/configuration/configuration.vue');
const SvisHealthComponent = () => import('@/admin/health/health.vue');
const SvisLogsComponent = () => import('@/admin/logs/logs.vue');
const SvisMetricsComponent = () => import('@/admin/metrics/metrics.vue');

export default [
  {
    path: '/admin/docs',
    name: 'SvisDocsComponent',
    component: SvisDocsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/health',
    name: 'SvisHealthComponent',
    component: SvisHealthComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/logs',
    name: 'SvisLogsComponent',
    component: SvisLogsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/metrics',
    name: 'SvisMetricsComponent',
    component: SvisMetricsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/configuration',
    name: 'SvisConfigurationComponent',
    component: SvisConfigurationComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
];
