import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Driver } from 'app/shared/model/driver.model';
import { DriverService } from './driver.service';
import { DriverComponent } from './driver.component';
import { DriverDetailComponent } from './driver-detail.component';
import { DriverUpdateComponent } from './driver-update.component';
import { IDriver } from 'app/shared/model/driver.model';

@Injectable({ providedIn: 'root' })
export class DriverResolve implements Resolve<IDriver> {
  constructor(private service: DriverService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDriver> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((driver: HttpResponse<Driver>) => driver.body));
    }
    return of(new Driver());
  }
}

export const driverRoute: Routes = [
  {
    path: '',
    component: DriverComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.driver.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DriverDetailComponent,
    resolve: {
      driver: DriverResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.driver.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DriverUpdateComponent,
    resolve: {
      driver: DriverResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.driver.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DriverUpdateComponent,
    resolve: {
      driver: DriverResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.driver.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
