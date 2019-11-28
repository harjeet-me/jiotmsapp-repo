import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Equipment } from 'app/shared/model/equipment.model';
import { EquipmentService } from './equipment.service';
import { EquipmentComponent } from './equipment.component';
import { EquipmentDetailComponent } from './equipment-detail.component';
import { EquipmentUpdateComponent } from './equipment-update.component';
import { IEquipment } from 'app/shared/model/equipment.model';

@Injectable({ providedIn: 'root' })
export class EquipmentResolve implements Resolve<IEquipment> {
  constructor(private service: EquipmentService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEquipment> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((equipment: HttpResponse<Equipment>) => equipment.body));
    }
    return of(new Equipment());
  }
}

export const equipmentRoute: Routes = [
  {
    path: '',
    component: EquipmentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.equipment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EquipmentDetailComponent,
    resolve: {
      equipment: EquipmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.equipment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EquipmentUpdateComponent,
    resolve: {
      equipment: EquipmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.equipment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EquipmentUpdateComponent,
    resolve: {
      equipment: EquipmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jiotmsappApp.equipment.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
