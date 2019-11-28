import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from './location.service';
import { LocationDeleteDialogComponent } from './location-delete-dialog.component';

@Component({
  selector: 'jhi-location',
  templateUrl: './location.component.html'
})
export class LocationComponent implements OnInit, OnDestroy {
  locations: ILocation[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected locationService: LocationService,
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
      this.locationService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ILocation[]>) => (this.locations = res.body));
      return;
    }
    this.locationService.query().subscribe((res: HttpResponse<ILocation[]>) => {
      this.locations = res.body;
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
    this.registerChangeInLocations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILocation) {
    return item.id;
  }

  registerChangeInLocations() {
    this.eventSubscriber = this.eventManager.subscribe('locationListModification', () => this.loadAll());
  }

  delete(location: ILocation) {
    const modalRef = this.modalService.open(LocationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.location = location;
  }
}
