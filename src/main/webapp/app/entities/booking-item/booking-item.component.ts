import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBookingItem } from 'app/shared/model/booking-item.model';
import { BookingItemService } from './booking-item.service';
import { BookingItemDeleteDialogComponent } from './booking-item-delete-dialog.component';

@Component({
  selector: 'jhi-booking-item',
  templateUrl: './booking-item.component.html'
})
export class BookingItemComponent implements OnInit, OnDestroy {
  bookingItems: IBookingItem[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected bookingItemService: BookingItemService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.bookingItemService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IBookingItem[]>) => (this.bookingItems = res.body));
      return;
    }
    this.bookingItemService.query().subscribe((res: HttpResponse<IBookingItem[]>) => {
      this.bookingItems = res.body;
      this.currentSearch = '';
    });
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInBookingItems();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBookingItem) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInBookingItems() {
    this.eventSubscriber = this.eventManager.subscribe('bookingItemListModification', () => this.loadAll());
  }

  delete(bookingItem: IBookingItem) {
    const modalRef = this.modalService.open(BookingItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bookingItem = bookingItem;
  }
}
