import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IVendor, Vendor } from 'app/shared/model/vendor.model';
import { VendorService } from './vendor.service';

@Component({
  selector: 'jhi-vendor-update',
  templateUrl: './vendor-update.component.html'
})
export class VendorUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    company: [],
    firstName: [],
    lastName: [],
    dot: [],
    mc: [],
    email: [],
    phoneNumber: [],
    insuranceProvider: []
  });

  constructor(protected vendorService: VendorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vendor }) => {
      this.updateForm(vendor);
    });
  }

  updateForm(vendor: IVendor) {
    this.editForm.patchValue({
      id: vendor.id,
      company: vendor.company,
      firstName: vendor.firstName,
      lastName: vendor.lastName,
      dot: vendor.dot,
      mc: vendor.mc,
      email: vendor.email,
      phoneNumber: vendor.phoneNumber,
      insuranceProvider: vendor.insuranceProvider
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vendor = this.createFromForm();
    if (vendor.id !== undefined) {
      this.subscribeToSaveResponse(this.vendorService.update(vendor));
    } else {
      this.subscribeToSaveResponse(this.vendorService.create(vendor));
    }
  }

  private createFromForm(): IVendor {
    return {
      ...new Vendor(),
      id: this.editForm.get(['id']).value,
      company: this.editForm.get(['company']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      dot: this.editForm.get(['dot']).value,
      mc: this.editForm.get(['mc']).value,
      email: this.editForm.get(['email']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      insuranceProvider: this.editForm.get(['insuranceProvider']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVendor>>) {
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
