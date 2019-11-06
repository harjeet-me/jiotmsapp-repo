import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBookingItem } from 'app/shared/model/booking-item.model';

@Component({
  selector: 'jhi-booking-item-detail',
  templateUrl: './booking-item-detail.component.html'
})
export class BookingItemDetailComponent implements OnInit {
  bookingItem: IBookingItem;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bookingItem }) => {
      this.bookingItem = bookingItem;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
