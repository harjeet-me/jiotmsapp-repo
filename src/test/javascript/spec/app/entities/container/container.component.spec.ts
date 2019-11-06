import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JiotmsappTestModule } from '../../../test.module';
import { ContainerComponent } from 'app/entities/container/container.component';
import { ContainerService } from 'app/entities/container/container.service';
import { Container } from 'app/shared/model/container.model';

describe('Component Tests', () => {
  describe('Container Management Component', () => {
    let comp: ContainerComponent;
    let fixture: ComponentFixture<ContainerComponent>;
    let service: ContainerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JiotmsappTestModule],
        declarations: [ContainerComponent],
        providers: []
      })
        .overrideTemplate(ContainerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContainerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContainerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Container(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.containers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
