import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { CustomerDeleteDialogComponent } from './customer-delete-dialog.component';

@Component({
  selector: 'jhi-customer',
  templateUrl: './customer.component.html'
})
export class CustomerComponent implements OnInit, OnDestroy {
  customers: ICustomer[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected customerService: CustomerService,
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
      this.customerService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body));
      return;
    }
    this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => {
      this.customers = res.body;
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
    this.registerChangeInCustomers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomer) {
    return item.id;
  }

  registerChangeInCustomers() {
    this.eventSubscriber = this.eventManager.subscribe('customerListModification', () => this.loadAll());
  }

  delete(customer: ICustomer) {
    const modalRef = this.modalService.open(CustomerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customer = customer;
  }
}
