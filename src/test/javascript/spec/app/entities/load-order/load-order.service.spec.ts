import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LoadOrderService } from 'app/entities/load-order/load-order.service';
import { ILoadOrder, LoadOrder } from 'app/shared/model/load-order.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';

describe('Service Tests', () => {
  describe('LoadOrder Service', () => {
    let injector: TestBed;
    let service: LoadOrderService;
    let httpMock: HttpTestingController;
    let elemDefault: ILoadOrder;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LoadOrderService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LoadOrder(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        StatusEnum.PICKEDUP,
        0,
        currentDate,
        'image/png',
        'AAAAAAA',
        false,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            pickup: currentDate.format(DATE_FORMAT),
            drop: currentDate.format(DATE_FORMAT),
            chasisInTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a LoadOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            pickup: currentDate.format(DATE_FORMAT),
            drop: currentDate.format(DATE_FORMAT),
            chasisInTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            pickup: currentDate,
            drop: currentDate,
            chasisInTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new LoadOrder(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a LoadOrder', () => {
        const returnedFromService = Object.assign(
          {
            orderNuber: 'BBBBBB',
            description: 'BBBBBB',
            shipmentNumber: 'BBBBBB',
            bol: 'BBBBBB',
            pickup: currentDate.format(DATE_FORMAT),
            drop: currentDate.format(DATE_FORMAT),
            pickupLocation: 'BBBBBB',
            dropLocation: 'BBBBBB',
            currentLocation: 'BBBBBB',
            status: 'BBBBBB',
            detention: 1,
            chasisInTime: currentDate.format(DATE_TIME_FORMAT),
            pod: 'BBBBBB',
            hazmat: true,
            recievedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            pickup: currentDate,
            drop: currentDate,
            chasisInTime: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of LoadOrder', () => {
        const returnedFromService = Object.assign(
          {
            orderNuber: 'BBBBBB',
            description: 'BBBBBB',
            shipmentNumber: 'BBBBBB',
            bol: 'BBBBBB',
            pickup: currentDate.format(DATE_FORMAT),
            drop: currentDate.format(DATE_FORMAT),
            pickupLocation: 'BBBBBB',
            dropLocation: 'BBBBBB',
            currentLocation: 'BBBBBB',
            status: 'BBBBBB',
            detention: 1,
            chasisInTime: currentDate.format(DATE_TIME_FORMAT),
            pod: 'BBBBBB',
            hazmat: true,
            recievedBy: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            pickup: currentDate,
            drop: currentDate,
            chasisInTime: currentDate
          },
          returnedFromService
        );
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

      it('should delete a LoadOrder', () => {
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
