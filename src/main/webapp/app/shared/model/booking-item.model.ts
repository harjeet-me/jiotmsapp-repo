import { Moment } from 'moment';
import { IEquipment } from 'app/shared/model/equipment.model';
import { IDriver } from 'app/shared/model/driver.model';
import { ILoadOrder } from 'app/shared/model/load-order.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';

export interface IBookingItem {
  id?: number;
  description?: string;
  pickup?: Moment;
  drop?: Moment;
  source?: string;
  destination?: string;
  currentLocation?: string;
  status?: StatusEnum;
  detention?: number;
  chasisInTime?: Moment;
  podContentType?: string;
  pod?: any;
  hazmat?: boolean;
  recievedBy?: string;
  equipment?: IEquipment[];
  drivers?: IDriver[];
  mainBooking?: ILoadOrder;
}

export class BookingItem implements IBookingItem {
  constructor(
    public id?: number,
    public description?: string,
    public pickup?: Moment,
    public drop?: Moment,
    public source?: string,
    public destination?: string,
    public currentLocation?: string,
    public status?: StatusEnum,
    public detention?: number,
    public chasisInTime?: Moment,
    public podContentType?: string,
    public pod?: any,
    public hazmat?: boolean,
    public recievedBy?: string,
    public equipment?: IEquipment[],
    public drivers?: IDriver[],
    public mainBooking?: ILoadOrder
  ) {
    this.hazmat = this.hazmat || false;
  }
}
