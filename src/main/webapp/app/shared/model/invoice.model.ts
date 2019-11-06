import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';

export interface IInvoice {
  id?: number;
  bookingNo?: string;
  invoiceTotal?: number;
  invoiceDueDate?: Moment;
  status?: StatusEnum;
  invoiceTo?: ICustomer;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public bookingNo?: string,
    public invoiceTotal?: number,
    public invoiceDueDate?: Moment,
    public status?: StatusEnum,
    public invoiceTo?: ICustomer
  ) {}
}
