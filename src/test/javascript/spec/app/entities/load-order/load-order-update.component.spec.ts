import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JiotmsappTestModule } from '../../../test.module';
import { LoadOrderUpdateComponent } from 'app/entities/load-order/load-order-update.component';
import { LoadOrderService } from 'app/entities/load-order/load-order.service';
import { LoadOrder } from 'app/shared/model/load-order.model';

describe('Component Tests', () => {
  describe('LoadOrder Management Update Component', () => {
    let comp: LoadOrderUpdateComponent;
    let fixture: ComponentFixture<LoadOrderUpdateComponent>;
    let service: LoadOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [LoadOrderUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LoadOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LoadOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoadOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LoadOrder(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new LoadOrder();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
