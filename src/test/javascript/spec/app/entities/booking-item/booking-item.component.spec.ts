import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JiotmsappTestModule } from '../../../test.module';
import { BookingItemComponent } from 'app/entities/booking-item/booking-item.component';
import { BookingItemService } from 'app/entities/booking-item/booking-item.service';
import { BookingItem } from 'app/shared/model/booking-item.model';

describe('Component Tests', () => {
  describe('BookingItem Management Component', () => {
    let comp: BookingItemComponent;
    let fixture: ComponentFixture<BookingItemComponent>;
    let service: BookingItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [BookingItemComponent],
        providers: []
      })
        .overrideTemplate(BookingItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookingItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookingItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BookingItem(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bookingItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
