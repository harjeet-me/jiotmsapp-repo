import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';

export interface IInvoiceItem {
  id?: number;
  name?: string;
  status?: StatusEnum;
  shipmentNumber?: string;
  bol?: string;
}

export class InvoiceItem implements IInvoiceItem {
  constructor(public id?: number, public name?: string, public status?: StatusEnum, public shipmentNumber?: string, public bol?: string) {}
}
