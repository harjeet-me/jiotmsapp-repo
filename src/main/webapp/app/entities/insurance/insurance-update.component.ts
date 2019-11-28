import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IInsurance, Insurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from './insurance.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-insurance-update',
  templateUrl: './insurance-update.component.html'
})
export class InsuranceUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];
  startDateDp: any;
  expiryDateDp: any;

  editForm = this.fb.group({
    id: [],
    providerNumber: [],
    provider: [],
    description: [],
    startDate: [],
    expiryDate: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected insuranceService: InsuranceService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ insurance }) => {
      this.updateForm(insurance);
    });
    this.customerService
      .query()
      .subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(insurance: IInsurance) {
    this.editForm.patchValue({
      id: insurance.id,
      providerNumber: insurance.providerNumber,
      provider: insurance.provider,
      description: insurance.description,
      startDate: insurance.startDate,
      expiryDate: insurance.expiryDate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const insurance = this.createFromForm();
    if (insurance.id !== undefined) {
      this.subscribeToSaveResponse(this.insuranceService.update(insurance));
    } else {
      this.subscribeToSaveResponse(this.insuranceService.create(insurance));
    }
  }

  private createFromForm(): IInsurance {
    return {
      ...new Insurance(),
      id: this.editForm.get(['id']).value,
      providerNumber: this.editForm.get(['providerNumber']).value,
      provider: this.editForm.get(['provider']).value,
      description: this.editForm.get(['description']).value,
      startDate: this.editForm.get(['startDate']).value,
      expiryDate: this.editForm.get(['expiryDate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsurance>>) {
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
