import { IBookingItem } from 'app/shared/model/booking-item.model';
import { EquipmentEnum } from 'app/shared/model/enumerations/equipment-enum.model';
import { SizeEnum } from 'app/shared/model/enumerations/size-enum.model';

export interface IEquipment {
  id?: number;
  number?: string;
  type?: EquipmentEnum;
  size?: SizeEnum;
  insurance?: string;
  bookingItem?: IBookingItem;
}

export class Equipment implements IEquipment {
  constructor(
    public id?: number,
    public number?: string,
    public type?: EquipmentEnum,
    public size?: SizeEnum,
    public insurance?: string,
    public bookingItem?: IBookingItem
  ) {}
}
