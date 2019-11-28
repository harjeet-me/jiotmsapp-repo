import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { EquipmentComponent } from './equipment.component';
import { EquipmentDetailComponent } from './equipment-detail.component';
import { EquipmentUpdateComponent } from './equipment-update.component';
import { EquipmentDeleteDialogComponent } from './equipment-delete-dialog.component';
import { equipmentRoute } from './equipment.route';

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(equipmentRoute)],
  declarations: [EquipmentComponent, EquipmentDetailComponent, EquipmentUpdateComponent, EquipmentDeleteDialogComponent],
  entryComponents: [EquipmentDeleteDialogComponent]
})
export class JiotmsappEquipmentModule {}
