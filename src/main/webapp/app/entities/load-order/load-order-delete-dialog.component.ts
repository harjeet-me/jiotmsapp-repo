import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILoadOrder } from 'app/shared/model/load-order.model';
import { LoadOrderService } from './load-order.service';

@Component({
  templateUrl: './load-order-delete-dialog.component.html'
})
export class LoadOrderDeleteDialogComponent {
  loadOrder: ILoadOrder;

  constructor(protected loadOrderService: LoadOrderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.loadOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'loadOrderListModification',
        content: 'Deleted an loadOrder'
      });
      this.activeModal.dismiss(true);
    });
  }
}
