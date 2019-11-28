import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IBookingItem, BookingItem } from 'app/shared/model/booking-item.model';
import { BookingItemService } from './booking-item.service';
import { ILoadOrder } from 'app/shared/model/load-order.model';
import { LoadOrderService } from 'app/entities/load-order/load-order.service';

@Component({
  selector: 'jhi-booking-item-update',
  templateUrl: './booking-item-update.component.html'
})
export class BookingItemUpdateComponent implements OnInit {
  isSaving: boolean;

  loadorders: ILoadOrder[];

  editForm = this.fb.group({
    id: [],
    description: [],
    pickup: [],
    drop: [],
    source: [],
    destination: [],
    currentLocation: [],
    status: [],
    detention: [],
    chasisInTime: [],
    pod: [],
    podContentType: [],
    hazmat: [],
    recievedBy: [],
    mainBooking: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected bookingItemService: BookingItemService,
    protected loadOrderService: LoadOrderService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bookingItem }) => {
      this.updateForm(bookingItem);
    });
    this.loadOrderService
      .query()
      .subscribe((res: HttpResponse<ILoadOrder[]>) => (this.loadorders = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(bookingItem: IBookingItem) {
    this.editForm.patchValue({
      id: bookingItem.id,
      description: bookingItem.description,
      pickup: bookingItem.pickup != null ? bookingItem.pickup.format(DATE_TIME_FORMAT) : null,
      drop: bookingItem.drop != null ? bookingItem.drop.format(DATE_TIME_FORMAT) : null,
      source: bookingItem.source,
      destination: bookingItem.destination,
      currentLocation: bookingItem.currentLocation,
      status: bookingItem.status,
      detention: bookingItem.detention,
      chasisInTime: bookingItem.chasisInTime != null ? bookingItem.chasisInTime.format(DATE_TIME_FORMAT) : null,
      pod: bookingItem.pod,
      podContentType: bookingItem.podContentType,
      hazmat: bookingItem.hazmat,
      recievedBy: bookingItem.recievedBy,
      mainBooking: bookingItem.mainBooking
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bookingItem = this.createFromForm();
    if (bookingItem.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingItemService.update(bookingItem));
    } else {
      this.subscribeToSaveResponse(this.bookingItemService.create(bookingItem));
    }
  }

  private createFromForm(): IBookingItem {
    return {
      ...new BookingItem(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      pickup: this.editForm.get(['pickup']).value != null ? moment(this.editForm.get(['pickup']).value, DATE_TIME_FORMAT) : undefined,
      drop: this.editForm.get(['drop']).value != null ? moment(this.editForm.get(['drop']).value, DATE_TIME_FORMAT) : undefined,
      source: this.editForm.get(['source']).value,
      destination: this.editForm.get(['destination']).value,
      currentLocation: this.editForm.get(['currentLocation']).value,
      status: this.editForm.get(['status']).value,
      detention: this.editForm.get(['detention']).value,
      chasisInTime:
        this.editForm.get(['chasisInTime']).value != null ? moment(this.editForm.get(['chasisInTime']).value, DATE_TIME_FORMAT) : undefined,
      podContentType: this.editForm.get(['podContentType']).value,
      pod: this.editForm.get(['pod']).value,
      hazmat: this.editForm.get(['hazmat']).value,
      recievedBy: this.editForm.get(['recievedBy']).value,
      mainBooking: this.editForm.get(['mainBooking']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookingItem>>) {
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

  trackLoadOrderById(index: number, item: ILoadOrder) {
    return item.id;
  }
}
