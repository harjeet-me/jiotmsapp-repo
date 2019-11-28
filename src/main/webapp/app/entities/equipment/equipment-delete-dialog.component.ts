import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipment } from 'app/shared/model/equipment.model';
import { EquipmentService } from './equipment.service';

@Component({
  templateUrl: './equipment-delete-dialog.component.html'
})
export class EquipmentDeleteDialogComponent {
  equipment: IEquipment;

  constructor(protected equipmentService: EquipmentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.equipmentService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'equipmentListModification',
        content: 'Deleted an equipment'
      });
      this.activeModal.dismiss(true);
    });
  }
}
