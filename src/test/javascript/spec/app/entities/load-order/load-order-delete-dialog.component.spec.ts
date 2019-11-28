import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JiotmsappTestModule } from '../../../test.module';
import { LoadOrderDeleteDialogComponent } from 'app/entities/load-order/load-order-delete-dialog.component';
import { LoadOrderService } from 'app/entities/load-order/load-order.service';

describe('Component Tests', () => {
  describe('LoadOrder Management Delete Component', () => {
    let comp: LoadOrderDeleteDialogComponent;
    let fixture: ComponentFixture<LoadOrderDeleteDialogComponent>;
    let service: LoadOrderService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [LoadOrderDeleteDialogComponent]
      })
        .overrideTemplate(LoadOrderDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LoadOrderDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoadOrderService);
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
