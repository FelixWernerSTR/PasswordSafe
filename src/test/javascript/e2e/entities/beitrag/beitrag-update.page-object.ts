import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class BeitragUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('svisInfoApp.beitrag.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  contentInput: ElementFinder = element(by.css('textarea#beitrag-content'));

  attribInput: ElementFinder = element(by.css('input#beitrag-attrib'));

  titelInput: ElementFinder = element(by.css('input#beitrag-titel'));

  rechteInput: ElementFinder = element(by.css('input#beitrag-rechte'));

  validFromInput: ElementFinder = element(by.css('input#beitrag-validFrom'));

  validToInput: ElementFinder = element(by.css('input#beitrag-validTo'));

  publishDateInput: ElementFinder = element(by.css('input#beitrag-publishDate'));

  archivSelect = element(by.css('select#beitrag-archiv'));
  themaSelect = element(by.css('select#beitrag-thema'));
}
