import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { LoadOrderComponent } from './load-order.component';
import { LoadOrderDetailComponent } from './load-order-detail.component';
import { LoadOrderUpdateComponent } from './load-order-update.component';
import { LoadOrderDeleteDialogComponent } from './load-order-delete-dialog.component';
import { loadOrderRoute } from './load-order.route';

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(loadOrderRoute)],
  declarations: [LoadOrderComponent, LoadOrderDetailComponent, LoadOrderUpdateComponent, LoadOrderDeleteDialogComponent],
  entryComponents: [LoadOrderDeleteDialogComponent]
})
export class JiotmsappLoadOrderModule {}
