import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BookingItemService } from 'app/entities/booking-item/booking-item.service';
import { IBookingItem, BookingItem } from 'app/shared/model/booking-item.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';

describe('Service Tests', () => {
  describe('BookingItem Service', () => {
    let injector: TestBed;
    let service: BookingItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IBookingItem;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(BookingItemService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BookingItem(
        0,
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
            pickup: currentDate.format(DATE_TIME_FORMAT),
            drop: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a BookingItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            pickup: currentDate.format(DATE_TIME_FORMAT),
            drop: currentDate.format(DATE_TIME_FORMAT),
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
          .create(new BookingItem(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a BookingItem', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            pickup: currentDate.format(DATE_TIME_FORMAT),
            drop: currentDate.format(DATE_TIME_FORMAT),
            source: 'BBBBBB',
            destination: 'BBBBBB',
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

      it('should return a list of BookingItem', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            pickup: currentDate.format(DATE_TIME_FORMAT),
            drop: currentDate.format(DATE_TIME_FORMAT),
            source: 'BBBBBB',
            destination: 'BBBBBB',
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

      it('should delete a BookingItem', () => {
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
