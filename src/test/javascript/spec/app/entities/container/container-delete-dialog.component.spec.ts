import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JiotmsappTestModule } from '../../../test.module';
import { ContainerDeleteDialogComponent } from 'app/entities/container/container-delete-dialog.component';
import { ContainerService } from 'app/entities/container/container.service';

describe('Component Tests', () => {
  describe('Container Management Delete Component', () => {
    let comp: ContainerDeleteDialogComponent;
    let fixture: ComponentFixture<ContainerDeleteDialogComponent>;
    let service: ContainerService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [ContainerDeleteDialogComponent]
      })
        .overrideTemplate(ContainerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContainerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContainerService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
