import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JiotmsappTestModule } from '../../../test.module';
import { DriverUpdateComponent } from 'app/entities/driver/driver-update.component';
import { DriverService } from 'app/entities/driver/driver.service';
import { Driver } from 'app/shared/model/driver.model';

describe('Component Tests', () => {
  describe('Driver Management Update Component', () => {
    let comp: DriverUpdateComponent;
    let fixture: ComponentFixture<DriverUpdateComponent>;
    let service: DriverService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [DriverUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DriverUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DriverUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DriverService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Driver(123);
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
        const entity = new Driver();
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
