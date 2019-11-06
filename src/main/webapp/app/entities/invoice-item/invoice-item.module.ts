import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { InvoiceItemComponent } from './invoice-item.component';
import { InvoiceItemDetailComponent } from './invoice-item-detail.component';
import { InvoiceItemUpdateComponent } from './invoice-item-update.component';
import { InvoiceItemDeletePopupComponent, InvoiceItemDeleteDialogComponent } from './invoice-item-delete-dialog.component';
import { invoiceItemRoute, invoiceItemPopupRoute } from './invoice-item.route';

const ENTITY_STATES = [...invoiceItemRoute, ...invoiceItemPopupRoute];

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InvoiceItemComponent,
    InvoiceItemDetailComponent,
    InvoiceItemUpdateComponent,
    InvoiceItemDeleteDialogComponent,
    InvoiceItemDeletePopupComponent
  ],
  entryComponents: [InvoiceItemDeleteDialogComponent]
})
export class JiotmsappInvoiceItemModule {}
