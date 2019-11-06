import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JiotmsappTestModule } from '../../../test.module';
import { VendorComponent } from 'app/entities/vendor/vendor.component';
import { VendorService } from 'app/entities/vendor/vendor.service';
import { Vendor } from 'app/shared/model/vendor.model';

describe('Component Tests', () => {
  describe('Vendor Management Component', () => {
    let comp: VendorComponent;
    let fixture: ComponentFixture<VendorComponent>;
    let service: VendorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [VendorComponent],
        providers: []
      })
        .overrideTemplate(VendorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VendorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VendorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Vendor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vendors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
