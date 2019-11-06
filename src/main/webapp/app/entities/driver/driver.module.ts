import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { DriverComponent } from './driver.component';
import { DriverDetailComponent } from './driver-detail.component';
import { DriverUpdateComponent } from './driver-update.component';
import { DriverDeletePopupComponent, DriverDeleteDialogComponent } from './driver-delete-dialog.component';
import { driverRoute, driverPopupRoute } from './driver.route';

const ENTITY_STATES = [...driverRoute, ...driverPopupRoute];

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DriverComponent, DriverDetailComponent, DriverUpdateComponent, DriverDeleteDialogComponent, DriverDeletePopupComponent],
  entryComponents: [DriverDeleteDialogComponent]
})
export class JiotmsappDriverModule {}
