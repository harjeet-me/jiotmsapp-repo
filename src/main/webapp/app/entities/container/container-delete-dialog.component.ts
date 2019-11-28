import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContainer } from 'app/shared/model/container.model';
import { ContainerService } from './container.service';

@Component({
  templateUrl: './container-delete-dialog.component.html'
})
export class ContainerDeleteDialogComponent {
  container: IContainer;

  constructor(protected containerService: ContainerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.containerService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'containerListModification',
        content: 'Deleted an container'
      });
      this.activeModal.dismiss(true);
    });
  }
}
