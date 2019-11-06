import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IInsurance {
  id?: number;
  providerNumber?: string;
  provider?: string;
  description?: string;
  startDate?: Moment;
  expiryDate?: Moment;
  customer?: ICustomer;
}

export class Insurance implements IInsurance {
  constructor(
    public id?: number,
    public providerNumber?: string,
    public provider?: string,
    public description?: string,
    public startDate?: Moment,
    public expiryDate?: Moment,
    public customer?: ICustomer
  ) {}
}
