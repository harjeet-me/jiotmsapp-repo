import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BookingItem } from 'app/shared/model/booking-item.model';
import { BookingItemService } from './booking-item.service';
import { BookingItemComponent } from './booking-item.component';
import { BookingItemDetailComponent } from './booking-item-detail.component';
import { BookingItemUpdateComponent } from './booking-item-update.component';
import { IBookingItem } from 'app/shared/model/booking-item.model';

@Injectable({ providedIn: 'root' })
export class BookingItemResolve implements Resolve<IBookingItem> {
  constructor(private service: BookingItemService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBookingItem> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((bookingItem: HttpResponse<BookingItem>) => bookingItem.body));
    }
    return of(new BookingItem());
  }
}

export const bookingItemRoute: Routes = [
  {
    path: '',
    component: BookingItemComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.bookingItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BookingItemDetailComponent,
    resolve: {
      bookingItem: BookingItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.bookingItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BookingItemUpdateComponent,
    resolve: {
      bookingItem: BookingItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.bookingItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BookingItemUpdateComponent,
    resolve: {
      bookingItem: BookingItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.bookingItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
