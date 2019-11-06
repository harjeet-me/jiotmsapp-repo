import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { EquipmentComponent } from './equipment.component';
import { EquipmentDetailComponent } from './equipment-detail.component';
import { EquipmentUpdateComponent } from './equipment-update.component';
import { EquipmentDeletePopupComponent, EquipmentDeleteDialogComponent } from './equipment-delete-dialog.component';
import { equipmentRoute, equipmentPopupRoute } from './equipment.route';

const ENTITY_STATES = [...equipmentRoute, ...equipmentPopupRoute];

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EquipmentComponent,
    EquipmentDetailComponent,
    EquipmentUpdateComponent,
    EquipmentDeleteDialogComponent,
    EquipmentDeletePopupComponent
  ],
  entryComponents: [EquipmentDeleteDialogComponent]
})
export class JiotmsappEquipmentModule {}
