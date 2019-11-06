import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IContainer } from 'app/shared/model/container.model';
import { AccountService } from 'app/core/auth/account.service';
import { ContainerService } from './container.service';

@Component({
  selector: 'jhi-container',
  templateUrl: './container.component.html'
})
export class ContainerComponent implements OnInit, OnDestroy {
  containers: IContainer[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected containerService: ContainerService,
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
      this.containerService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IContainer[]>) => res.ok),
          map((res: HttpResponse<IContainer[]>) => res.body)
        )
        .subscribe((res: IContainer[]) => (this.containers = res));
      return;
    }
    this.containerService
      .query()
      .pipe(
        filter((res: HttpResponse<IContainer[]>) => res.ok),
        map((res: HttpResponse<IContainer[]>) => res.body)
      )
      .subscribe((res: IContainer[]) => {
        this.containers = res;
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
    this.registerChangeInContainers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContainer) {
    return item.id;
  }

  registerChangeInContainers() {
    this.eventSubscriber = this.eventManager.subscribe('containerListModification', response => this.loadAll());
  }
}
