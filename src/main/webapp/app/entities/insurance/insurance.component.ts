import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IInsurance } from 'app/shared/model/insurance.model';
import { AccountService } from 'app/core/auth/account.service';
import { InsuranceService } from './insurance.service';

@Component({
  selector: 'jhi-insurance',
  templateUrl: './insurance.component.html'
})
export class InsuranceComponent implements OnInit, OnDestroy {
  insurances: IInsurance[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected insuranceService: InsuranceService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
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
        .pipe(
          filter((res: HttpResponse<IInsurance[]>) => res.ok),
          map((res: HttpResponse<IInsurance[]>) => res.body)
        )
        .subscribe((res: IInsurance[]) => (this.insurances = res));
      return;
    }
    this.insuranceService
      .query()
      .pipe(
        filter((res: HttpResponse<IInsurance[]>) => res.ok),
        map((res: HttpResponse<IInsurance[]>) => res.body)
      )
      .subscribe((res: IInsurance[]) => {
        this.insurances = res;
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
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInsurances();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInsurance) {
    return item.id;
  }

  registerChangeInInsurances() {
    this.eventSubscriber = this.eventManager.subscribe('insuranceListModification', response => this.loadAll());
  }
}
