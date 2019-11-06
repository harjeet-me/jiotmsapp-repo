import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBooking, Booking } from 'app/shared/model/booking.model';
import { BookingService } from './booking.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-booking-update',
  templateUrl: './booking-update.component.html'
})
export class BookingUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];

  editForm = this.fb.group({
    id: [],
    name: [],
    loadNuber: [],
    shipmentNumber: [],
    bol: [],
    status: [],
    customer: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected bookingService: BookingService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ booking }) => {
      this.updateForm(booking);
    });
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(booking: IBooking) {
    this.editForm.patchValue({
      id: booking.id,
      name: booking.name,
      loadNuber: booking.loadNuber,
      shipmentNumber: booking.shipmentNumber,
      bol: booking.bol,
      status: booking.status,
      customer: booking.customer
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const booking = this.createFromForm();
    if (booking.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingService.update(booking));
    } else {
      this.subscribeToSaveResponse(this.bookingService.create(booking));
    }
  }

  private createFromForm(): IBooking {
    return {
      ...new Booking(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      loadNuber: this.editForm.get(['loadNuber']).value,
      shipmentNumber: this.editForm.get(['shipmentNumber']).value,
      bol: this.editForm.get(['bol']).value,
      status: this.editForm.get(['status']).value,
      customer: this.editForm.get(['customer']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBooking>>) {
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
