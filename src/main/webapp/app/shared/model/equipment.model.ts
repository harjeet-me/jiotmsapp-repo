import { EquipmentEnum } from 'app/shared/model/enumerations/equipment-enum.model';
import { SizeEnum } from 'app/shared/model/enumerations/size-enum.model';

export interface IEquipment {
  id?: number;
  number?: string;
  type?: EquipmentEnum;
  size?: SizeEnum;
  insurance?: string;
}

export class Equipment implements IEquipment {
  constructor(public id?: number, public number?: string, public type?: EquipmentEnum, public size?: SizeEnum, public insurance?: string) {}
}
