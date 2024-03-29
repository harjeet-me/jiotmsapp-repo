import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { InvoiceItemService } from 'app/entities/invoice-item/invoice-item.service';
import { IInvoiceItem, InvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

describe('Service Tests', () => {
  describe('InvoiceItem Service', () => {
    let injector: TestBed;
    let service: InvoiceItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IInvoiceItem;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InvoiceItemService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new InvoiceItem(0, 'AAAAAAA', 0, 0, 0, InvoiceStatus.DRAFT, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a InvoiceItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new InvoiceItem(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a InvoiceItem', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            qty: 1,
            price: 1,
            total: 1,
            status: 'BBBBBB',
            shipmentNumber: 'BBBBBB',
            bol: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of InvoiceItem', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            qty: 1,
            price: 1,
            total: 1,
            status: 'BBBBBB',
            shipmentNumber: 'BBBBBB',
            bol: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a InvoiceItem', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
