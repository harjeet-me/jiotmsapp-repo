import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILoadOrder } from 'app/shared/model/load-order.model';

type EntityResponseType = HttpResponse<ILoadOrder>;
type EntityArrayResponseType = HttpResponse<ILoadOrder[]>;

@Injectable({ providedIn: 'root' })
export class LoadOrderService {
  public resourceUrl = SERVER_API_URL + 'api/load-orders';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/load-orders';

  constructor(protected http: HttpClient) {}

  create(loadOrder: ILoadOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loadOrder);
    return this.http
      .post<ILoadOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(loadOrder: ILoadOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loadOrder);
    return this.http
      .put<ILoadOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILoadOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILoadOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILoadOrder[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(loadOrder: ILoadOrder): ILoadOrder {
    const copy: ILoadOrder = Object.assign({}, loadOrder, {
      pickup: loadOrder.pickup != null && loadOrder.pickup.isValid() ? loadOrder.pickup.format(DATE_FORMAT) : null,
      drop: loadOrder.drop != null && loadOrder.drop.isValid() ? loadOrder.drop.format(DATE_FORMAT) : null,
      chasisInTime: loadOrder.chasisInTime != null && loadOrder.chasisInTime.isValid() ? loadOrder.chasisInTime.toJSON() : null
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
      res.body.forEach((loadOrder: ILoadOrder) => {
        loadOrder.pickup = loadOrder.pickup != null ? moment(loadOrder.pickup) : null;
        loadOrder.drop = loadOrder.drop != null ? moment(loadOrder.drop) : null;
        loadOrder.chasisInTime = loadOrder.chasisInTime != null ? moment(loadOrder.chasisInTime) : null;
      });
    }
    return res;
  }
}
