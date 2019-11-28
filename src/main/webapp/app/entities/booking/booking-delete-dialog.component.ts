import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBooking } from 'app/shared/model/booking.model';
import { BookingService } from './booking.service';

@Component({
  templateUrl: './booking-delete-dialog.component.html'
})
export class BookingDeleteDialogComponent {
  booking: IBooking;

  constructor(protected bookingService: BookingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.bookingService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'bookingListModification',
        content: 'Deleted an booking'
      });
      this.activeModal.dismiss(true);
    });
  }
}
