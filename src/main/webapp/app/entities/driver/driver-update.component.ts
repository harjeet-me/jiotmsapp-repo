import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDriver, Driver } from 'app/shared/model/driver.model';
import { DriverService } from './driver.service';
import { IBookingItem } from 'app/shared/model/booking-item.model';
import { BookingItemService } from 'app/entities/booking-item/booking-item.service';

@Component({
  selector: 'jhi-driver-update',
  templateUrl: './driver-update.component.html'
})
export class DriverUpdateComponent implements OnInit {
  isSaving: boolean;

  bookingitems: IBookingItem[];
  dobDp: any;

  editForm = this.fb.group({
    id: [],
    company: [],
    firstName: [],
    lastName: [],
    email: [],
    phoneNumber: [],
    licenceNumber: [],
    dob: [],
    bookingItem: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected driverService: DriverService,
    protected bookingItemService: BookingItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ driver }) => {
      this.updateForm(driver);
    });
    this.bookingItemService
      .query()
      .subscribe(
        (res: HttpResponse<IBookingItem[]>) => (this.bookingitems = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(driver: IDriver) {
    this.editForm.patchValue({
      id: driver.id,
      company: driver.company,
      firstName: driver.firstName,
      lastName: driver.lastName,
      email: driver.email,
      phoneNumber: driver.phoneNumber,
      licenceNumber: driver.licenceNumber,
      dob: driver.dob,
      bookingItem: driver.bookingItem
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const driver = this.createFromForm();
    if (driver.id !== undefined) {
      this.subscribeToSaveResponse(this.driverService.update(driver));
    } else {
      this.subscribeToSaveResponse(this.driverService.create(driver));
    }
  }

  private createFromForm(): IDriver {
    return {
      ...new Driver(),
      id: this.editForm.get(['id']).value,
      company: this.editForm.get(['company']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      email: this.editForm.get(['email']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      licenceNumber: this.editForm.get(['licenceNumber']).value,
      dob: this.editForm.get(['dob']).value,
      bookingItem: this.editForm.get(['bookingItem']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDriver>>) {
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

  trackBookingItemById(index: number, item: IBookingItem) {
    return item.id;
  }
}
