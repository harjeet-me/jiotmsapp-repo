import { IBookingItem } from 'app/shared/model/booking-item.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';

export interface IBooking {
  id?: number;
  name?: string;
  loadNuber?: string;
  shipmentNumber?: string;
  bol?: string;
  status?: StatusEnum;
  bookingItems?: IBookingItem[];
  customer?: ICustomer;
}

export class Booking implements IBooking {
  constructor(
    public id?: number,
    public name?: string,
    public loadNuber?: string,
    public shipmentNumber?: string,
    public bol?: string,
    public status?: StatusEnum,
    public bookingItems?: IBookingItem[],
    public customer?: ICustomer
  ) {}
}
