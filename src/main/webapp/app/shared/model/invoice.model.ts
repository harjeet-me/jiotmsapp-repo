import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

export interface IInvoice {
  id?: number;
  orderNo?: string;
  invoiceTaxTotal?: number;
  invoiceSubTotal?: number;
  invoiceTotal?: number;
  invoiceDate?: Moment;
  invoiceDueDate?: Moment;
  status?: InvoiceStatus;
  invoiceTo?: ICustomer;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public orderNo?: string,
    public invoiceTaxTotal?: number,
    public invoiceSubTotal?: number,
    public invoiceTotal?: number,
    public invoiceDate?: Moment,
    public invoiceDueDate?: Moment,
    public status?: InvoiceStatus,
    public invoiceTo?: ICustomer
  ) {}
}
