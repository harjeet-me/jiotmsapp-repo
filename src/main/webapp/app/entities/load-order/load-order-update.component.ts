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
import { ILoadOrder, LoadOrder } from 'app/shared/model/load-order.model';
import { LoadOrderService } from './load-order.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-load-order-update',
  templateUrl: './load-order-update.component.html'
})
export class LoadOrderUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];
  pickupDp: any;
  dropDp: any;

  editForm = this.fb.group({
    id: [],
    orderNuber: [],
    description: [],
    shipmentNumber: [],
    bol: [],
    pickup: [],
    drop: [],
    pickupLocation: [],
    dropLocation: [],
    currentLocation: [],
    status: [],
    detention: [],
    chasisInTime: [],
    pod: [],
    podContentType: [],
    hazmat: [],
    recievedBy: [],
    customer: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected loadOrderService: LoadOrderService,
    protected customerService: CustomerService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ loadOrder }) => {
      this.updateForm(loadOrder);
    });
    this.customerService
      .query()
      .subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(loadOrder: ILoadOrder) {
    this.editForm.patchValue({
      id: loadOrder.id,
      orderNuber: loadOrder.orderNuber,
      description: loadOrder.description,
      shipmentNumber: loadOrder.shipmentNumber,
      bol: loadOrder.bol,
      pickup: loadOrder.pickup,
      drop: loadOrder.drop,
      pickupLocation: loadOrder.pickupLocation,
      dropLocation: loadOrder.dropLocation,
      currentLocation: loadOrder.currentLocation,
      status: loadOrder.status,
      detention: loadOrder.detention,
      chasisInTime: loadOrder.chasisInTime != null ? loadOrder.chasisInTime.format(DATE_TIME_FORMAT) : null,
      pod: loadOrder.pod,
      podContentType: loadOrder.podContentType,
      hazmat: loadOrder.hazmat,
      recievedBy: loadOrder.recievedBy,
      customer: loadOrder.customer
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
    const loadOrder = this.createFromForm();
    if (loadOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.loadOrderService.update(loadOrder));
    } else {
      this.subscribeToSaveResponse(this.loadOrderService.create(loadOrder));
    }
  }

  private createFromForm(): ILoadOrder {
    return {
      ...new LoadOrder(),
      id: this.editForm.get(['id']).value,
      orderNuber: this.editForm.get(['orderNuber']).value,
      description: this.editForm.get(['description']).value,
      shipmentNumber: this.editForm.get(['shipmentNumber']).value,
      bol: this.editForm.get(['bol']).value,
      pickup: this.editForm.get(['pickup']).value,
      drop: this.editForm.get(['drop']).value,
      pickupLocation: this.editForm.get(['pickupLocation']).value,
      dropLocation: this.editForm.get(['dropLocation']).value,
      currentLocation: this.editForm.get(['currentLocation']).value,
      status: this.editForm.get(['status']).value,
      detention: this.editForm.get(['detention']).value,
      chasisInTime:
        this.editForm.get(['chasisInTime']).value != null ? moment(this.editForm.get(['chasisInTime']).value, DATE_TIME_FORMAT) : undefined,
      podContentType: this.editForm.get(['podContentType']).value,
      pod: this.editForm.get(['pod']).value,
      hazmat: this.editForm.get(['hazmat']).value,
      recievedBy: this.editForm.get(['recievedBy']).value,
      customer: this.editForm.get(['customer']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoadOrder>>) {
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
