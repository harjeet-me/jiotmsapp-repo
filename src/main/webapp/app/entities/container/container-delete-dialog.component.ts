import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContainer } from 'app/shared/model/container.model';
import { ContainerService } from './container.service';

@Component({
  selector: 'jhi-container-delete-dialog',
  templateUrl: './container-delete-dialog.component.html'
})
export class ContainerDeleteDialogComponent {
  container: IContainer;

  constructor(protected containerService: ContainerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.containerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'containerListModification',
        content: 'Deleted an container'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-container-delete-popup',
  template: ''
})
export class ContainerDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ container }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContainerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.container = container;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/container', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/container', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
