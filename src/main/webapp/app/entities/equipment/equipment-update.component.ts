import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEquipment, Equipment } from 'app/shared/model/equipment.model';
import { EquipmentService } from './equipment.service';

@Component({
  selector: 'jhi-equipment-update',
  templateUrl: './equipment-update.component.html'
})
export class EquipmentUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    number: [],
    type: [],
    size: [],
    insurance: []
  });

  constructor(protected equipmentService: EquipmentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ equipment }) => {
      this.updateForm(equipment);
    });
  }

  updateForm(equipment: IEquipment) {
    this.editForm.patchValue({
      id: equipment.id,
      number: equipment.number,
      type: equipment.type,
      size: equipment.size,
      insurance: equipment.insurance
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const equipment = this.createFromForm();
    if (equipment.id !== undefined) {
      this.subscribeToSaveResponse(this.equipmentService.update(equipment));
    } else {
      this.subscribeToSaveResponse(this.equipmentService.create(equipment));
    }
  }

  private createFromForm(): IEquipment {
    return {
      ...new Equipment(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      type: this.editForm.get(['type']).value,
      size: this.editForm.get(['size']).value,
      insurance: this.editForm.get(['insurance']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipment>>) {
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
