import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILoadOrder } from 'app/shared/model/load-order.model';

@Component({
  selector: 'jhi-load-order-detail',
  templateUrl: './load-order-detail.component.html'
})
export class LoadOrderDetailComponent implements OnInit {
  loadOrder: ILoadOrder;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ loadOrder }) => {
      this.loadOrder = loadOrder;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
