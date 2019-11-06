import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IInvoiceItem, InvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';

@Component({
  selector: 'jhi-invoice-item-update',
  templateUrl: './invoice-item-update.component.html'
})
export class InvoiceItemUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    status: [],
    shipmentNumber: [],
    bol: []
  });

  constructor(protected invoiceItemService: InvoiceItemService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invoiceItem }) => {
      this.updateForm(invoiceItem);
    });
  }

  updateForm(invoiceItem: IInvoiceItem) {
    this.editForm.patchValue({
      id: invoiceItem.id,
      name: invoiceItem.name,
      status: invoiceItem.status,
      shipmentNumber: invoiceItem.shipmentNumber,
      bol: invoiceItem.bol
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const invoiceItem = this.createFromForm();
    if (invoiceItem.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceItemService.update(invoiceItem));
    } else {
      this.subscribeToSaveResponse(this.invoiceItemService.create(invoiceItem));
    }
  }

  private createFromForm(): IInvoiceItem {
    return {
      ...new InvoiceItem(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      status: this.editForm.get(['status']).value,
      shipmentNumber: this.editForm.get(['shipmentNumber']).value,
      bol: this.editForm.get(['bol']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceItem>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
