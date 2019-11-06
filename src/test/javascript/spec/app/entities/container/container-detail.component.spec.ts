import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JiotmsappTestModule } from '../../../test.module';
import { ContainerDetailComponent } from 'app/entities/container/container-detail.component';
import { Container } from 'app/shared/model/container.model';

describe('Component Tests', () => {
  describe('Container Management Detail Component', () => {
    let comp: ContainerDetailComponent;
    let fixture: ComponentFixture<ContainerDetailComponent>;
    const route = ({ data: of({ container: new Container(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [ContainerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContainerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContainerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.container).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
