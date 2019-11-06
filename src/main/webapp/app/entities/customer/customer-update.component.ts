import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact/contact.service';
import { IInsurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from 'app/entities/insurance/insurance.service';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
  isSaving: boolean;

  billingaddresses: ILocation[];

  contacts: IContact[];

  insurances: IInsurance[];

  editForm = this.fb.group({
    id: [],
    company: [],
    firstName: [],
    lastName: [],
    email: [],
    dot: [],
    mc: [],
    phoneNumber: [],
    address: [],
    streetAddress: [],
    city: [],
    stateProvince: [],
    country: [],
    postalCode: [],
    billingAddress: [null, Validators.required],
    contact: [],
    insurance: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customerService: CustomerService,
    protected locationService: LocationService,
    protected contactService: ContactService,
    protected insuranceService: InsuranceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);
    });
    this.locationService
      .query({ filter: 'customer-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ILocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocation[]>) => response.body)
      )
      .subscribe(
        (res: ILocation[]) => {
          if (!this.editForm.get('billingAddress').value || !this.editForm.get('billingAddress').value.id) {
            this.billingaddresses = res;
          } else {
            this.locationService
              .find(this.editForm.get('billingAddress').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ILocation>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ILocation>) => subResponse.body)
              )
              .subscribe(
                (subRes: ILocation) => (this.billingaddresses = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.contactService
      .query({ filter: 'customer-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IContact[]>) => mayBeOk.ok),
        map((response: HttpResponse<IContact[]>) => response.body)
      )
      .subscribe(
        (res: IContact[]) => {
          if (!this.editForm.get('contact').value || !this.editForm.get('contact').value.id) {
            this.contacts = res;
          } else {
            this.contactService
              .find(this.editForm.get('contact').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IContact>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IContact>) => subResponse.body)
              )
              .subscribe(
                (subRes: IContact) => (this.contacts = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.insuranceService
      .query({ filter: 'customer-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IInsurance[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInsurance[]>) => response.body)
      )
      .subscribe(
        (res: IInsurance[]) => {
          if (!this.editForm.get('insurance').value || !this.editForm.get('insurance').value.id) {
            this.insurances = res;
          } else {
            this.insuranceService
              .find(this.editForm.get('insurance').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IInsurance>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IInsurance>) => subResponse.body)
              )
              .subscribe(
                (subRes: IInsurance) => (this.insurances = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(customer: ICustomer) {
    this.editForm.patchValue({
      id: customer.id,
      company: customer.company,
      firstName: customer.firstName,
      lastName: customer.lastName,
      email: customer.email,
      dot: customer.dot,
      mc: customer.mc,
      phoneNumber: customer.phoneNumber,
      address: customer.address,
      streetAddress: customer.streetAddress,
      city: customer.city,
      stateProvince: customer.stateProvince,
      country: customer.country,
      postalCode: customer.postalCode,
      billingAddress: customer.billingAddress,
      contact: customer.contact,
      insurance: customer.insurance
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id']).value,
      company: this.editForm.get(['company']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      email: this.editForm.get(['email']).value,
      dot: this.editForm.get(['dot']).value,
      mc: this.editForm.get(['mc']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      address: this.editForm.get(['address']).value,
      streetAddress: this.editForm.get(['streetAddress']).value,
      city: this.editForm.get(['city']).value,
      stateProvince: this.editForm.get(['stateProvince']).value,
      country: this.editForm.get(['country']).value,
      postalCode: this.editForm.get(['postalCode']).value,
      billingAddress: this.editForm.get(['billingAddress']).value,
      contact: this.editForm.get(['contact']).value,
      insurance: this.editForm.get(['insurance']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>) {
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

  trackLocationById(index: number, item: ILocation) {
    return item.id;
  }

  trackContactById(index: number, item: IContact) {
    return item.id;
  }

  trackInsuranceById(index: number, item: IInsurance) {
    return item.id;
  }
}
