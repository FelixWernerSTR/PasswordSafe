import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class GroupUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('passwortverwaltungApp.group.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#group-name'));

  descriptionInput: ElementFinder = element(by.css('input#group-description'));
}
