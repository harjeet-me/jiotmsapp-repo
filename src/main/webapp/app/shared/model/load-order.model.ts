import { Moment } from 'moment';
import { IBookingItem } from 'app/shared/model/booking-item.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';

export interface ILoadOrder {
  id?: number;
  orderNuber?: string;
  description?: string;
  shipmentNumber?: string;
  bol?: string;
  pickup?: Moment;
  drop?: Moment;
  pickupLocation?: string;
  dropLocation?: string;
  currentLocation?: string;
  status?: StatusEnum;
  detention?: number;
  chasisInTime?: Moment;
  podContentType?: string;
  pod?: any;
  hazmat?: boolean;
  recievedBy?: string;
  bookingItems?: IBookingItem[];
  customer?: ICustomer;
}

export class LoadOrder implements ILoadOrder {
  constructor(
    public id?: number,
    public orderNuber?: string,
    public description?: string,
    public shipmentNumber?: string,
    public bol?: string,
    public pickup?: Moment,
    public drop?: Moment,
    public pickupLocation?: string,
    public dropLocation?: string,
    public currentLocation?: string,
    public status?: StatusEnum,
    public detention?: number,
    public chasisInTime?: Moment,
    public podContentType?: string,
    public pod?: any,
    public hazmat?: boolean,
    public recievedBy?: string,
    public bookingItems?: IBookingItem[],
    public customer?: ICustomer
  ) {
    this.hazmat = this.hazmat || false;
  }
}
