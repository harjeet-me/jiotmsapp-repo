import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInsurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from './insurance.service';
import { InsuranceDeleteDialogComponent } from './insurance-delete-dialog.component';

@Component({
  selector: 'jhi-insurance',
  templateUrl: './insurance.component.html'
})
export class InsuranceComponent implements OnInit, OnDestroy {
  insurances: IInsurance[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected insuranceService: InsuranceService,
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
      this.insuranceService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IInsurance[]>) => (this.insurances = res.body));
      return;
    }
    this.insuranceService.query().subscribe((res: HttpResponse<IInsurance[]>) => {
      this.insurances = res.body;
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
    this.registerChangeInInsurances();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInsurance) {
    return item.id;
  }

  registerChangeInInsurances() {
    this.eventSubscriber = this.eventManager.subscribe('insuranceListModification', () => this.loadAll());
  }

  delete(insurance: IInsurance) {
    const modalRef = this.modalService.open(InsuranceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.insurance = insurance;
  }
}
