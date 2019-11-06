import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IContainer, Container } from 'app/shared/model/container.model';
import { ContainerService } from './container.service';

@Component({
  selector: 'jhi-container-update',
  templateUrl: './container-update.component.html'
})
export class ContainerUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    company: [],
    firstName: [],
    lastName: [],
    email: [],
    phoneNumber: [],
    insuranceProvider: []
  });

  constructor(protected containerService: ContainerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ container }) => {
      this.updateForm(container);
    });
  }

  updateForm(container: IContainer) {
    this.editForm.patchValue({
      id: container.id,
      company: container.company,
      firstName: container.firstName,
      lastName: container.lastName,
      email: container.email,
      phoneNumber: container.phoneNumber,
      insuranceProvider: container.insuranceProvider
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const container = this.createFromForm();
    if (container.id !== undefined) {
      this.subscribeToSaveResponse(this.containerService.update(container));
    } else {
      this.subscribeToSaveResponse(this.containerService.create(container));
    }
  }

  private createFromForm(): IContainer {
    return {
      ...new Container(),
      id: this.editForm.get(['id']).value,
      company: this.editForm.get(['company']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      email: this.editForm.get(['email']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      insuranceProvider: this.editForm.get(['insuranceProvider']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContainer>>) {
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
