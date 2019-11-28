import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { BookingItemComponent } from './booking-item.component';
import { BookingItemDetailComponent } from './booking-item-detail.component';
import { BookingItemUpdateComponent } from './booking-item-update.component';
import { BookingItemDeleteDialogComponent } from './booking-item-delete-dialog.component';
import { bookingItemRoute } from './booking-item.route';

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(bookingItemRoute)],
  declarations: [BookingItemComponent, BookingItemDetailComponent, BookingItemUpdateComponent, BookingItemDeleteDialogComponent],
  entryComponents: [BookingItemDeleteDialogComponent]
})
export class JiotmsappBookingItemModule {}
