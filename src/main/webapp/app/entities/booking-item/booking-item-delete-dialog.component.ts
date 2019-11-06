import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookingItem } from 'app/shared/model/booking-item.model';
import { BookingItemService } from './booking-item.service';

@Component({
  selector: 'jhi-booking-item-delete-dialog',
  templateUrl: './booking-item-delete-dialog.component.html'
})
export class BookingItemDeleteDialogComponent {
  bookingItem: IBookingItem;

  constructor(
    protected bookingItemService: BookingItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.bookingItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'bookingItemListModification',
        content: 'Deleted an bookingItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-booking-item-delete-popup',
  template: ''
})
export class BookingItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bookingItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BookingItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.bookingItem = bookingItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/booking-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/booking-item', { outlets: { popup: null } }]);
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
