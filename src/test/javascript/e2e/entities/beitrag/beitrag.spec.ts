/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import BeitragComponentsPage, { BeitragDeleteDialog } from './beitrag.page-object';
import BeitragUpdatePage from './beitrag-update.page-object';
import BeitragDetailsPage from './beitrag-details.page-object';

import {
  clear,
  click,
  getRecordsCount,
  isVisible,
  selectLastOption,
  waitUntilAllDisplayed,
  waitUntilAnyDisplayed,
  waitUntilCount,
  waitUntilDisplayed,
  waitUntilHidden,
} from '../../util/utils';

const expect = chai.expect;

describe('Beitrag e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: BeitragUpdatePage;
  let detailsPage: BeitragDetailsPage;
  let listPage: BeitragComponentsPage;
  let deleteDialog: BeitragDeleteDialog;
  let beforeRecordsCount = 0;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login(username, password);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load Beitrags', async () => {
    await navBarPage.getEntityPage('beitrag');
    listPage = new BeitragComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create Beitrag page', async () => {
      await listPage.createButton.click();
      updatePage = new BeitragUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/svisInfoApp.beitrag.home.createOrEditLabel/);
    });

    it('should create and save Beitrags', async () => {
      await waitUntilDisplayed(updatePage.contentInput);
      await updatePage.contentInput.sendKeys('content');

      await updatePage.attribInput.sendKeys('attrib');

      await updatePage.titelInput.sendKeys('titel');

      await updatePage.rechteInput.sendKeys('rechte');

      await updatePage.validFromInput.sendKeys('2001-01-01');

      await updatePage.validToInput.sendKeys('2001-01-01');

      await updatePage.publishDateInput.sendKeys('2001-01-01');

      await selectLastOption(updatePage.archivSelect);

      // await selectLastOption(updatePage.themaSelect);

      expect(await updatePage.saveButton.isEnabled()).to.be.true;
      await updatePage.saveButton.click();

      await waitUntilHidden(updatePage.saveButton);
      expect(await isVisible(updatePage.saveButton)).to.be.false;

      await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      expect(await listPage.records.count()).to.eq(beforeRecordsCount + 1);
    });

    describe('Details, Update, Delete flow', () => {
      after(async () => {
        const deleteButton = listPage.getDeleteButton(listPage.records.first());
        await click(deleteButton);

        deleteDialog = new BeitragDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/svisInfoApp.beitrag.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details Beitrag page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new BeitragDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit Beitrag page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.contentInput.clear();
        await updatePage.contentInput.sendKeys('updatedcontent');

        await updatePage.attribInput.clear();
        await updatePage.attribInput.sendKeys('modified');

        await updatePage.titelInput.clear();
        await updatePage.titelInput.sendKeys('modified');

        await updatePage.rechteInput.clear();
        await updatePage.rechteInput.sendKeys('modified');

        await updatePage.validFromInput.clear();
        await updatePage.validFromInput.sendKeys('2019-01-01');

        await updatePage.validToInput.clear();
        await updatePage.validToInput.sendKeys('2019-01-01');

        await updatePage.publishDateInput.clear();
        await updatePage.publishDateInput.sendKeys('2019-01-01');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
