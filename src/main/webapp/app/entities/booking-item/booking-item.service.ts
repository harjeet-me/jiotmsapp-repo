import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBookingItem } from 'app/shared/model/booking-item.model';

type EntityResponseType = HttpResponse<IBookingItem>;
type EntityArrayResponseType = HttpResponse<IBookingItem[]>;

@Injectable({ providedIn: 'root' })
export class BookingItemService {
  public resourceUrl = SERVER_API_URL + 'api/booking-items';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/booking-items';

  constructor(protected http: HttpClient) {}

  create(bookingItem: IBookingItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookingItem);
    return this.http
      .post<IBookingItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bookingItem: IBookingItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookingItem);
    return this.http
      .put<IBookingItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBookingItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBookingItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBookingItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(bookingItem: IBookingItem): IBookingItem {
    const copy: IBookingItem = Object.assign({}, bookingItem, {
      pickup: bookingItem.pickup != null && bookingItem.pickup.isValid() ? bookingItem.pickup.toJSON() : null,
      drop: bookingItem.drop != null && bookingItem.drop.isValid() ? bookingItem.drop.toJSON() : null,
      chasisInTime: bookingItem.chasisInTime != null && bookingItem.chasisInTime.isValid() ? bookingItem.chasisInTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.pickup = res.body.pickup != null ? moment(res.body.pickup) : null;
      res.body.drop = res.body.drop != null ? moment(res.body.drop) : null;
      res.body.chasisInTime = res.body.chasisInTime != null ? moment(res.body.chasisInTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bookingItem: IBookingItem) => {
        bookingItem.pickup = bookingItem.pickup != null ? moment(bookingItem.pickup) : null;
        bookingItem.drop = bookingItem.drop != null ? moment(bookingItem.drop) : null;
        bookingItem.chasisInTime = bookingItem.chasisInTime != null ? moment(bookingItem.chasisInTime) : null;
      });
    }
    return res;
  }
}
