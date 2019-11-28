import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookingItem } from 'app/shared/model/booking-item.model';
import { BookingItemService } from './booking-item.service';

@Component({
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
    this.bookingItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'bookingItemListModification',
        content: 'Deleted an bookingItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}
