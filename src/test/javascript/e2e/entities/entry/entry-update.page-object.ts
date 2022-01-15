import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class EntryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('passwortverwaltungApp.entry.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  descriptionInput: ElementFinder = element(by.css('input#entry-description'));

  loginInput: ElementFinder = element(by.css('input#entry-login'));

  passwortInput: ElementFinder = element(by.css('input#entry-passwort'));

  passwortReplayInput: ElementFinder = element(by.css('input#entry-passwortReplay'));

  groupSelect = element(by.css('select#entry-group'));
}
