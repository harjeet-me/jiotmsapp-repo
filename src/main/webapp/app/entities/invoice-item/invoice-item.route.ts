import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { InvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';
import { InvoiceItemComponent } from './invoice-item.component';
import { InvoiceItemDetailComponent } from './invoice-item-detail.component';
import { InvoiceItemUpdateComponent } from './invoice-item-update.component';
import { IInvoiceItem } from 'app/shared/model/invoice-item.model';

@Injectable({ providedIn: 'root' })
export class InvoiceItemResolve implements Resolve<IInvoiceItem> {
  constructor(private service: InvoiceItemService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvoiceItem> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((invoiceItem: HttpResponse<InvoiceItem>) => invoiceItem.body));
    }
    return of(new InvoiceItem());
  }
}

export const invoiceItemRoute: Routes = [
  {
    path: '',
    component: InvoiceItemComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.invoiceItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvoiceItemDetailComponent,
    resolve: {
      invoiceItem: InvoiceItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.invoiceItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvoiceItemUpdateComponent,
    resolve: {
      invoiceItem: InvoiceItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.invoiceItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvoiceItemUpdateComponent,
    resolve: {
      invoiceItem: InvoiceItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.invoiceItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
