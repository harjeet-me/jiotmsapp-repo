import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Booking } from 'app/shared/model/booking.model';
import { BookingService } from './booking.service';
import { BookingComponent } from './booking.component';
import { BookingDetailComponent } from './booking-detail.component';
import { BookingUpdateComponent } from './booking-update.component';
import { IBooking } from 'app/shared/model/booking.model';

@Injectable({ providedIn: 'root' })
export class BookingResolve implements Resolve<IBooking> {
  constructor(private service: BookingService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBooking> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((booking: HttpResponse<Booking>) => booking.body));
    }
    return of(new Booking());
  }
}

export const bookingRoute: Routes = [
  {
    path: '',
    component: BookingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.booking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BookingDetailComponent,
    resolve: {
      booking: BookingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.booking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BookingUpdateComponent,
    resolve: {
      booking: BookingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.booking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BookingUpdateComponent,
    resolve: {
      booking: BookingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.booking.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
