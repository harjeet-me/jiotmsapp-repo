import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVendor } from 'app/shared/model/vendor.model';
import { VendorService } from './vendor.service';

@Component({
  selector: 'jhi-vendor-delete-dialog',
  templateUrl: './vendor-delete-dialog.component.html'
})
export class VendorDeleteDialogComponent {
  vendor: IVendor;

  constructor(protected vendorService: VendorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vendorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'vendorListModification',
        content: 'Deleted an vendor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vendor-delete-popup',
  template: ''
})
export class VendorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vendor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VendorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vendor = vendor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/vendor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/vendor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
