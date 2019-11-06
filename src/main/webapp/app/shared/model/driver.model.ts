import { Moment } from 'moment';
import { IBookingItem } from 'app/shared/model/booking-item.model';

export interface IDriver {
  id?: number;
  company?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: number;
  licenceNumber?: number;
  dob?: Moment;
  bookingItems?: IBookingItem[];
}

export class Driver implements IDriver {
  constructor(
    public id?: number,
    public company?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: number,
    public licenceNumber?: number,
    public dob?: Moment,
    public bookingItems?: IBookingItem[]
  ) {}
}
