import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JiotmsappTestModule } from '../../../test.module';
import { BookingItemUpdateComponent } from 'app/entities/booking-item/booking-item-update.component';
import { BookingItemService } from 'app/entities/booking-item/booking-item.service';
import { BookingItem } from 'app/shared/model/booking-item.model';

describe('Component Tests', () => {
  describe('BookingItem Management Update Component', () => {
    let comp: BookingItemUpdateComponent;
    let fixture: ComponentFixture<BookingItemUpdateComponent>;
    let service: BookingItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [BookingItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BookingItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookingItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookingItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BookingItem(123);
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
        const entity = new BookingItem();
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
