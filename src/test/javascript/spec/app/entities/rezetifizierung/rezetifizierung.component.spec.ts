/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RezetifizierungComponent from '@/entities/rezetifizierung/rezetifizierung.vue';
import RezetifizierungClass from '@/entities/rezetifizierung/rezetifizierung.component';
import RezetifizierungService from '@/entities/rezetifizierung/rezetifizierung.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Rezetifizierung Management Component', () => {
    let wrapper: Wrapper<RezetifizierungClass>;
    let comp: RezetifizierungClass;
    let rezetifizierungServiceStub: SinonStubbedInstance<RezetifizierungService>;

    beforeEach(() => {
      rezetifizierungServiceStub = sinon.createStubInstance<RezetifizierungService>(RezetifizierungService);
      rezetifizierungServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RezetifizierungClass>(RezetifizierungComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          rezetifizierungService: () => rezetifizierungServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      rezetifizierungServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRezetifizierungs();
      await comp.$nextTick();

      // THEN
      expect(rezetifizierungServiceStub.retrieve.called).toBeTruthy();
      expect(comp.rezetifizierungs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      rezetifizierungServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeRezetifizierung();
      await comp.$nextTick();

      // THEN
      expect(rezetifizierungServiceStub.delete.called).toBeTruthy();
      expect(rezetifizierungServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
