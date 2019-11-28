import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JiotmsappTestModule } from '../../../test.module';
import { LoadOrderDetailComponent } from 'app/entities/load-order/load-order-detail.component';
import { LoadOrder } from 'app/shared/model/load-order.model';

describe('Component Tests', () => {
  describe('LoadOrder Management Detail Component', () => {
    let comp: LoadOrderDetailComponent;
    let fixture: ComponentFixture<LoadOrderDetailComponent>;
    const route = ({ data: of({ loadOrder: new LoadOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [LoadOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LoadOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LoadOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.loadOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
