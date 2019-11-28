import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVendor } from 'app/shared/model/vendor.model';
import { VendorService } from './vendor.service';

@Component({
  templateUrl: './vendor-delete-dialog.component.html'
})
export class VendorDeleteDialogComponent {
  vendor: IVendor;

  constructor(protected vendorService: VendorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vendorService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'vendorListModification',
        content: 'Deleted an vendor'
      });
      this.activeModal.dismiss(true);
    });
  }
}
