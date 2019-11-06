import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { BookingComponent } from './booking.component';
import { BookingDetailComponent } from './booking-detail.component';
import { BookingUpdateComponent } from './booking-update.component';
import { BookingDeletePopupComponent, BookingDeleteDialogComponent } from './booking-delete-dialog.component';
import { bookingRoute, bookingPopupRoute } from './booking.route';

const ENTITY_STATES = [...bookingRoute, ...bookingPopupRoute];

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BookingComponent,
    BookingDetailComponent,
    BookingUpdateComponent,
    BookingDeleteDialogComponent,
    BookingDeletePopupComponent
  ],
  entryComponents: [BookingDeleteDialogComponent]
})
export class JiotmsappBookingModule {}
