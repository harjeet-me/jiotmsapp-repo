import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from './contact.service';

@Component({
  templateUrl: './contact-delete-dialog.component.html'
})
export class ContactDeleteDialogComponent {
  contact: IContact;

  constructor(protected contactService: ContactService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contactService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'contactListModification',
        content: 'Deleted an contact'
      });
      this.activeModal.dismiss(true);
    });
  }
}
