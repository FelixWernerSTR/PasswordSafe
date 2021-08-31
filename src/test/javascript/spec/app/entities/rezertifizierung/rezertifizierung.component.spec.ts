/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RezertifizierungComponent from '@/entities/rezertifizierung/rezertifizierung.vue';
import RezertifizierungClass from '@/entities/rezertifizierung/rezertifizierung.component';
import RezertifizierungService from '@/entities/rezertifizierung/rezertifizierung.service';

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
  describe('Rezertifizierung Management Component', () => {
    let wrapper: Wrapper<RezertifizierungClass>;
    let comp: RezertifizierungClass;
    let rezertifizierungServiceStub: SinonStubbedInstance<RezertifizierungService>;

    beforeEach(() => {
      rezertifizierungServiceStub = sinon.createStubInstance<RezertifizierungService>(RezertifizierungService);
      rezertifizierungServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RezertifizierungClass>(RezertifizierungComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          rezertifizierungService: () => rezertifizierungServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      rezertifizierungServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRezertifizierungs();
      await comp.$nextTick();

      // THEN
      expect(rezertifizierungServiceStub.retrieve.called).toBeTruthy();
      expect(comp.rezertifizierungs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      rezertifizierungServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeRezertifizierung();
      await comp.$nextTick();

      // THEN
      expect(rezertifizierungServiceStub.delete.called).toBeTruthy();
      expect(rezertifizierungServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
