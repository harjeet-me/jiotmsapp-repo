import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IDriver } from 'app/shared/model/driver.model';
import { AccountService } from 'app/core/auth/account.service';
import { DriverService } from './driver.service';

@Component({
  selector: 'jhi-driver',
  templateUrl: './driver.component.html'
})
export class DriverComponent implements OnInit, OnDestroy {
  drivers: IDriver[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected driverService: DriverService,
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
      this.driverService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IDriver[]>) => res.ok),
          map((res: HttpResponse<IDriver[]>) => res.body)
        )
        .subscribe((res: IDriver[]) => (this.drivers = res));
      return;
    }
    this.driverService
      .query()
      .pipe(
        filter((res: HttpResponse<IDriver[]>) => res.ok),
        map((res: HttpResponse<IDriver[]>) => res.body)
      )
      .subscribe((res: IDriver[]) => {
        this.drivers = res;
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
    this.registerChangeInDrivers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDriver) {
    return item.id;
  }

  registerChangeInDrivers() {
    this.eventSubscriber = this.eventManager.subscribe('driverListModification', response => this.loadAll());
  }
}
