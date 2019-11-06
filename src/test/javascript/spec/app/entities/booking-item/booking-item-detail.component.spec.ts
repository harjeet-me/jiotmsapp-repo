import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JiotmsappTestModule } from '../../../test.module';
import { BookingItemDetailComponent } from 'app/entities/booking-item/booking-item-detail.component';
import { BookingItem } from 'app/shared/model/booking-item.model';

describe('Component Tests', () => {
  describe('BookingItem Management Detail Component', () => {
    let comp: BookingItemDetailComponent;
    let fixture: ComponentFixture<BookingItemDetailComponent>;
    const route = ({ data: of({ bookingItem: new BookingItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [BookingItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BookingItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookingItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookingItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
