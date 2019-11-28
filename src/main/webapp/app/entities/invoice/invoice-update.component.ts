import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IInvoice, Invoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html'
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];
  invoiceDateDp: any;
  invoiceDueDateDp: any;

  editForm = this.fb.group({
    id: [],
    orderNo: [],
    invoiceTaxTotal: [],
    invoiceSubTotal: [],
    invoiceTotal: [],
    invoiceDate: [],
    invoiceDueDate: [],
    status: [],
    invoiceTo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected invoiceService: InvoiceService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.updateForm(invoice);
    });
    this.customerService
      .query()
      .subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(invoice: IInvoice) {
    this.editForm.patchValue({
      id: invoice.id,
      orderNo: invoice.orderNo,
      invoiceTaxTotal: invoice.invoiceTaxTotal,
      invoiceSubTotal: invoice.invoiceSubTotal,
      invoiceTotal: invoice.invoiceTotal,
      invoiceDate: invoice.invoiceDate,
      invoiceDueDate: invoice.invoiceDueDate,
      status: invoice.status,
      invoiceTo: invoice.invoiceTo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  private createFromForm(): IInvoice {
    return {
      ...new Invoice(),
      id: this.editForm.get(['id']).value,
      orderNo: this.editForm.get(['orderNo']).value,
      invoiceTaxTotal: this.editForm.get(['invoiceTaxTotal']).value,
      invoiceSubTotal: this.editForm.get(['invoiceSubTotal']).value,
      invoiceTotal: this.editForm.get(['invoiceTotal']).value,
      invoiceDate: this.editForm.get(['invoiceDate']).value,
      invoiceDueDate: this.editForm.get(['invoiceDueDate']).value,
      status: this.editForm.get(['status']).value,
      invoiceTo: this.editForm.get(['invoiceTo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }
}
