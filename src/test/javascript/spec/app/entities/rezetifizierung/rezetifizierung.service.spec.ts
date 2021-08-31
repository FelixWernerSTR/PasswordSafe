/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import RezetifizierungService from '@/entities/rezetifizierung/rezetifizierung.service';
import { Rezetifizierung } from '@/shared/model/rezetifizierung.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Rezetifizierung Service', () => {
    let service: RezetifizierungService;
    let elemDefault;

    beforeEach(() => {
      service = new RezetifizierungService();
      elemDefault = new Rezetifizierung(
        123,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Rezetifizierung', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Rezetifizierung', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Rezetifizierung', async () => {
        const returnedFromService = Object.assign(
          {
            loginName: 'BBBBBB',
            nachname: 'BBBBBB',
            vorname: 'BBBBBB',
            vermittlerNummer: 1,
            rollenZugehoerigkeiten: 'BBBBBB',
            dvcVertreterNummer: 'BBBBBB',
            dvcBenutzerGruppe: 'BBBBBB',
            icisSrGebaude: 'BBBBBB',
            icisSrHaftpflicht: 'BBBBBB',
            icisSrLeitungswasser: 'BBBBBB',
            icisSrKfzKasko: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Rezetifizierung', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Rezetifizierung', async () => {
        const patchObject = Object.assign(
          {
            loginName: 'BBBBBB',
            nachname: 'BBBBBB',
            vermittlerNummer: 1,
            dvcBenutzerGruppe: 'BBBBBB',
            icisSrGebaude: 'BBBBBB',
            icisSrLeitungswasser: 'BBBBBB',
          },
          new Rezetifizierung()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Rezetifizierung', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Rezetifizierung', async () => {
        const returnedFromService = Object.assign(
          {
            loginName: 'BBBBBB',
            nachname: 'BBBBBB',
            vorname: 'BBBBBB',
            vermittlerNummer: 1,
            rollenZugehoerigkeiten: 'BBBBBB',
            dvcVertreterNummer: 'BBBBBB',
            dvcBenutzerGruppe: 'BBBBBB',
            icisSrGebaude: 'BBBBBB',
            icisSrHaftpflicht: 'BBBBBB',
            icisSrLeitungswasser: 'BBBBBB',
            icisSrKfzKasko: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Rezetifizierung', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Rezetifizierung', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Rezetifizierung', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
