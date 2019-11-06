import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Vendor } from 'app/shared/model/vendor.model';
import { VendorService } from './vendor.service';
import { VendorComponent } from './vendor.component';
import { VendorDetailComponent } from './vendor-detail.component';
import { VendorUpdateComponent } from './vendor-update.component';
import { VendorDeletePopupComponent } from './vendor-delete-dialog.component';
import { IVendor } from 'app/shared/model/vendor.model';

@Injectable({ providedIn: 'root' })
export class VendorResolve implements Resolve<IVendor> {
  constructor(private service: VendorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVendor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Vendor>) => response.ok),
        map((vendor: HttpResponse<Vendor>) => vendor.body)
      );
    }
    return of(new Vendor());
  }
}

export const vendorRoute: Routes = [
  {
    path: '',
    component: VendorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.vendor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VendorDetailComponent,
    resolve: {
      vendor: VendorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.vendor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VendorUpdateComponent,
    resolve: {
      vendor: VendorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.vendor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VendorUpdateComponent,
    resolve: {
      vendor: VendorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.vendor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vendorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VendorDeletePopupComponent,
    resolve: {
      vendor: VendorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.vendor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];