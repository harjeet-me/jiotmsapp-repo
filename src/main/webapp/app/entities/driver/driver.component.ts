import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDriver } from 'app/shared/model/driver.model';
import { DriverService } from './driver.service';
import { DriverDeleteDialogComponent } from './driver-delete-dialog.component';

@Component({
  selector: 'jhi-driver',
  templateUrl: './driver.component.html'
})
export class DriverComponent implements OnInit, OnDestroy {
  drivers: IDriver[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected driverService: DriverService,
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
      this.driverService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IDriver[]>) => (this.drivers = res.body));
      return;
    }
    this.driverService.query().subscribe((res: HttpResponse<IDriver[]>) => {
      this.drivers = res.body;
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
    this.registerChangeInDrivers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDriver) {
    return item.id;
  }

  registerChangeInDrivers() {
    this.eventSubscriber = this.eventManager.subscribe('driverListModification', () => this.loadAll());
  }

  delete(driver: IDriver) {
    const modalRef = this.modalService.open(DriverDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.driver = driver;
  }
}
