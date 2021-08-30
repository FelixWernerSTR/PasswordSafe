/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RezetifizierungDetailComponent from '@/entities/rezetifizierung/rezetifizierung-details.vue';
import RezetifizierungClass from '@/entities/rezetifizierung/rezetifizierung-details.component';
import RezetifizierungService from '@/entities/rezetifizierung/rezetifizierung.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Rezetifizierung Management Detail Component', () => {
    let wrapper: Wrapper<RezetifizierungClass>;
    let comp: RezetifizierungClass;
    let rezetifizierungServiceStub: SinonStubbedInstance<RezetifizierungService>;

    beforeEach(() => {
      rezetifizierungServiceStub = sinon.createStubInstance<RezetifizierungService>(RezetifizierungService);

      wrapper = shallowMount<RezetifizierungClass>(RezetifizierungDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { rezetifizierungService: () => rezetifizierungServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRezetifizierung = { id: 123 };
        rezetifizierungServiceStub.find.resolves(foundRezetifizierung);

        // WHEN
        comp.retrieveRezetifizierung(123);
        await comp.$nextTick();

        // THEN
        expect(comp.rezetifizierung).toBe(foundRezetifizierung);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRezetifizierung = { id: 123 };
        rezetifizierungServiceStub.find.resolves(foundRezetifizierung);

        // WHEN
        comp.beforeRouteEnter({ params: { rezetifizierungId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.rezetifizierung).toBe(foundRezetifizierung);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
