import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';
import { InvoiceItemDeleteDialogComponent } from './invoice-item-delete-dialog.component';

@Component({
  selector: 'jhi-invoice-item',
  templateUrl: './invoice-item.component.html'
})
export class InvoiceItemComponent implements OnInit, OnDestroy {
  invoiceItems: IInvoiceItem[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected invoiceItemService: InvoiceItemService,
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
      this.invoiceItemService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IInvoiceItem[]>) => (this.invoiceItems = res.body));
      return;
    }
    this.invoiceItemService.query().subscribe((res: HttpResponse<IInvoiceItem[]>) => {
      this.invoiceItems = res.body;
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
    this.registerChangeInInvoiceItems();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInvoiceItem) {
    return item.id;
  }

  registerChangeInInvoiceItems() {
    this.eventSubscriber = this.eventManager.subscribe('invoiceItemListModification', () => this.loadAll());
  }

  delete(invoiceItem: IInvoiceItem) {
    const modalRef = this.modalService.open(InvoiceItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invoiceItem = invoiceItem;
  }
}
