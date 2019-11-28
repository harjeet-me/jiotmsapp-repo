import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEquipment } from 'app/shared/model/equipment.model';
import { EquipmentService } from './equipment.service';
import { EquipmentDeleteDialogComponent } from './equipment-delete-dialog.component';

@Component({
  selector: 'jhi-equipment',
  templateUrl: './equipment.component.html'
})
export class EquipmentComponent implements OnInit, OnDestroy {
  equipment: IEquipment[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected equipmentService: EquipmentService,
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
      this.equipmentService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IEquipment[]>) => (this.equipment = res.body));
      return;
    }
    this.equipmentService.query().subscribe((res: HttpResponse<IEquipment[]>) => {
      this.equipment = res.body;
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
    this.registerChangeInEquipment();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEquipment) {
    return item.id;
  }

  registerChangeInEquipment() {
    this.eventSubscriber = this.eventManager.subscribe('equipmentListModification', () => this.loadAll());
  }

  delete(equipment: IEquipment) {
    const modalRef = this.modalService.open(EquipmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.equipment = equipment;
  }
}
