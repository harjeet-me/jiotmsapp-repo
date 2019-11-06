import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipment } from 'app/shared/model/equipment.model';
import { AccountService } from 'app/core/auth/account.service';
import { EquipmentService } from './equipment.service';

@Component({
  selector: 'jhi-equipment',
  templateUrl: './equipment.component.html'
})
export class EquipmentComponent implements OnInit, OnDestroy {
  equipment: IEquipment[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected equipmentService: EquipmentService,
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
      this.equipmentService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IEquipment[]>) => res.ok),
          map((res: HttpResponse<IEquipment[]>) => res.body)
        )
        .subscribe((res: IEquipment[]) => (this.equipment = res));
      return;
    }
    this.equipmentService
      .query()
      .pipe(
        filter((res: HttpResponse<IEquipment[]>) => res.ok),
        map((res: HttpResponse<IEquipment[]>) => res.body)
      )
      .subscribe((res: IEquipment[]) => {
        this.equipment = res;
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
    this.registerChangeInEquipment();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEquipment) {
    return item.id;
  }

  registerChangeInEquipment() {
    this.eventSubscriber = this.eventManager.subscribe('equipmentListModification', response => this.loadAll());
  }
}
