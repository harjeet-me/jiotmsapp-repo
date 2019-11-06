import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { ContainerComponent } from './container.component';
import { ContainerDetailComponent } from './container-detail.component';
import { ContainerUpdateComponent } from './container-update.component';
import { ContainerDeletePopupComponent, ContainerDeleteDialogComponent } from './container-delete-dialog.component';
import { containerRoute, containerPopupRoute } from './container.route';

const ENTITY_STATES = [...containerRoute, ...containerPopupRoute];

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContainerComponent,
    ContainerDetailComponent,
    ContainerUpdateComponent,
    ContainerDeleteDialogComponent,
    ContainerDeletePopupComponent
  ],
  entryComponents: [ContainerDeleteDialogComponent]
})
export class JiotmsappContainerModule {}
