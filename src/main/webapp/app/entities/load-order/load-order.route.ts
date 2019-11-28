import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LoadOrder } from 'app/shared/model/load-order.model';
import { LoadOrderService } from './load-order.service';
import { LoadOrderComponent } from './load-order.component';
import { LoadOrderDetailComponent } from './load-order-detail.component';
import { LoadOrderUpdateComponent } from './load-order-update.component';
import { ILoadOrder } from 'app/shared/model/load-order.model';

@Injectable({ providedIn: 'root' })
export class LoadOrderResolve implements Resolve<ILoadOrder> {
  constructor(private service: LoadOrderService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoadOrder> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((loadOrder: HttpResponse<LoadOrder>) => loadOrder.body));
    }
    return of(new LoadOrder());
  }
}

export const loadOrderRoute: Routes = [
  {
    path: '',
    component: LoadOrderComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jiotmsappApp.loadOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LoadOrderDetailComponent,
    resolve: {
      loadOrder: LoadOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.loadOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LoadOrderUpdateComponent,
    resolve: {
      loadOrder: LoadOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.loadOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LoadOrderUpdateComponent,
    resolve: {
      loadOrder: LoadOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.loadOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
