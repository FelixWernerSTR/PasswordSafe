import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ThemaUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('svisInfoApp.thema.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#thema-name'));

  rechteInput: ElementFinder = element(by.css('input#thema-rechte'));

  displaycountInput: ElementFinder = element(by.css('input#thema-displaycount'));

  thementypSelect = element(by.css('select#thema-thementyp'));
}
