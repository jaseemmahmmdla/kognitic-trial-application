import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIndicationType, IndicationType } from 'app/shared/model/indication-type.model';
import { IndicationTypeService } from './indication-type.service';
import { IIndication } from 'app/shared/model/indication.model';
import { IndicationService } from 'app/entities/indication/indication.service';

@Component({
  selector: 'jhi-indication-type-update',
  templateUrl: './indication-type-update.component.html',
})
export class IndicationTypeUpdateComponent implements OnInit {
  isSaving = false;
  indications: IIndication[] = [];

  editForm = this.fb.group({
    id: [],
    indicationType: [],
    indication: [],
  });

  constructor(
    protected indicationTypeService: IndicationTypeService,
    protected indicationService: IndicationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicationType }) => {
      this.updateForm(indicationType);

      this.indicationService.query().subscribe((res: HttpResponse<IIndication[]>) => (this.indications = res.body || []));
    });
  }

  updateForm(indicationType: IIndicationType): void {
    this.editForm.patchValue({
      id: indicationType.id,
      indicationType: indicationType.indicationType,
      indication: indicationType.indication,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const indicationType = this.createFromForm();
    if (indicationType.id !== undefined) {
      this.subscribeToSaveResponse(this.indicationTypeService.update(indicationType));
    } else {
      this.subscribeToSaveResponse(this.indicationTypeService.create(indicationType));
    }
  }

  private createFromForm(): IIndicationType {
    return {
      ...new IndicationType(),
      id: this.editForm.get(['id'])!.value,
      indicationType: this.editForm.get(['indicationType'])!.value,
      indication: this.editForm.get(['indication'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndicationType>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IIndication): any {
    return item.id;
  }
}
