import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

export interface IInvoiceItem {
  id?: number;
  description?: string;
  qty?: number;
  price?: number;
  total?: number;
  status?: InvoiceStatus;
  shipmentNumber?: string;
  bol?: string;
}

export class InvoiceItem implements IInvoiceItem {
  constructor(
    public id?: number,
    public description?: string,
    public qty?: number,
    public price?: number,
    public total?: number,
    public status?: InvoiceStatus,
    public shipmentNumber?: string,
    public bol?: string
  ) {}
}
