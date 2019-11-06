import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JiotmsappTestModule } from '../../../test.module';
import { InvoiceItemUpdateComponent } from 'app/entities/invoice-item/invoice-item-update.component';
import { InvoiceItemService } from 'app/entities/invoice-item/invoice-item.service';
import { InvoiceItem } from 'app/shared/model/invoice-item.model';

describe('Component Tests', () => {
  describe('InvoiceItem Management Update Component', () => {
    let comp: InvoiceItemUpdateComponent;
    let fixture: ComponentFixture<InvoiceItemUpdateComponent>;
    let service: InvoiceItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [InvoiceItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InvoiceItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoiceItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InvoiceItem(123);
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
        const entity = new InvoiceItem();
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
