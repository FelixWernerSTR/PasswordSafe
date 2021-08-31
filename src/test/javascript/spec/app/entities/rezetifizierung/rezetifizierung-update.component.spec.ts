/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import RezetifizierungUpdateComponent from '@/entities/rezetifizierung/rezetifizierung-update.vue';
import RezetifizierungClass from '@/entities/rezetifizierung/rezetifizierung-update.component';
import RezetifizierungService from '@/entities/rezetifizierung/rezetifizierung.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Rezetifizierung Management Update Component', () => {
    let wrapper: Wrapper<RezetifizierungClass>;
    let comp: RezetifizierungClass;
    let rezetifizierungServiceStub: SinonStubbedInstance<RezetifizierungService>;

    beforeEach(() => {
      rezetifizierungServiceStub = sinon.createStubInstance<RezetifizierungService>(RezetifizierungService);

      wrapper = shallowMount<RezetifizierungClass>(RezetifizierungUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          rezetifizierungService: () => rezetifizierungServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.rezetifizierung = entity;
        rezetifizierungServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rezetifizierungServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.rezetifizierung = entity;
        rezetifizierungServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rezetifizierungServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRezetifizierung = { id: 123 };
        rezetifizierungServiceStub.find.resolves(foundRezetifizierung);
        rezetifizierungServiceStub.retrieve.resolves([foundRezetifizierung]);

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
