import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { BookingItemComponent } from './booking-item.component';
import { BookingItemDetailComponent } from './booking-item-detail.component';
import { BookingItemUpdateComponent } from './booking-item-update.component';
import { BookingItemDeletePopupComponent, BookingItemDeleteDialogComponent } from './booking-item-delete-dialog.component';
import { bookingItemRoute, bookingItemPopupRoute } from './booking-item.route';

const ENTITY_STATES = [...bookingItemRoute, ...bookingItemPopupRoute];

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BookingItemComponent,
    BookingItemDetailComponent,
    BookingItemUpdateComponent,
    BookingItemDeleteDialogComponent,
    BookingItemDeletePopupComponent
  ],
  entryComponents: [BookingItemDeleteDialogComponent]
})
export class JiotmsappBookingItemModule {}
