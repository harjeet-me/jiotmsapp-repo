import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContainer } from 'app/shared/model/container.model';
import { ContainerService } from './container.service';
import { ContainerDeleteDialogComponent } from './container-delete-dialog.component';

@Component({
  selector: 'jhi-container',
  templateUrl: './container.component.html'
})
export class ContainerComponent implements OnInit, OnDestroy {
  containers: IContainer[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected containerService: ContainerService,
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
      this.containerService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IContainer[]>) => (this.containers = res.body));
      return;
    }
    this.containerService.query().subscribe((res: HttpResponse<IContainer[]>) => {
      this.containers = res.body;
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
    this.registerChangeInContainers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContainer) {
    return item.id;
  }

  registerChangeInContainers() {
    this.eventSubscriber = this.eventManager.subscribe('containerListModification', () => this.loadAll());
  }

  delete(container: IContainer) {
    const modalRef = this.modalService.open(ContainerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.container = container;
  }
}
