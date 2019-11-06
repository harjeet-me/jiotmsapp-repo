import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Container } from 'app/shared/model/container.model';
import { ContainerService } from './container.service';
import { ContainerComponent } from './container.component';
import { ContainerDetailComponent } from './container-detail.component';
import { ContainerUpdateComponent } from './container-update.component';
import { ContainerDeletePopupComponent } from './container-delete-dialog.component';
import { IContainer } from 'app/shared/model/container.model';

@Injectable({ providedIn: 'root' })
export class ContainerResolve implements Resolve<IContainer> {
  constructor(private service: ContainerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContainer> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Container>) => response.ok),
        map((container: HttpResponse<Container>) => container.body)
      );
    }
    return of(new Container());
  }
}

export const containerRoute: Routes = [
  {
    path: '',
    component: ContainerComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.container.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContainerDetailComponent,
    resolve: {
      container: ContainerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.container.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContainerUpdateComponent,
    resolve: {
      container: ContainerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.container.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContainerUpdateComponent,
    resolve: {
      container: ContainerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.container.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const containerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ContainerDeletePopupComponent,
    resolve: {
      container: ContainerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.container.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
