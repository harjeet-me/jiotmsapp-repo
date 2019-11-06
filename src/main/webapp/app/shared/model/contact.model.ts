import { ICustomer } from 'app/shared/model/customer.model';
import { DesignationEnum } from 'app/shared/model/enumerations/designation-enum.model';

export interface IContact {
  id?: number;
  designation?: DesignationEnum;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: number;
  customer?: ICustomer;
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public designation?: DesignationEnum,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: number,
    public customer?: ICustomer
  ) {}
}
