import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStage, Stage } from 'app/shared/model/stage.model';
import { StageService } from './stage.service';
import { IIndication } from 'app/shared/model/indication.model';
import { IndicationService } from 'app/entities/indication/indication.service';

@Component({
  selector: 'jhi-stage-update',
  templateUrl: './stage-update.component.html',
})
export class StageUpdateComponent implements OnInit {
  isSaving = false;
  indications: IIndication[] = [];

  editForm = this.fb.group({
    id: [],
    stage: [],
    indication: [],
  });

  constructor(
    protected stageService: StageService,
    protected indicationService: IndicationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stage }) => {
      this.updateForm(stage);

      this.indicationService.query().subscribe((res: HttpResponse<IIndication[]>) => (this.indications = res.body || []));
    });
  }

  updateForm(stage: IStage): void {
    this.editForm.patchValue({
      id: stage.id,
      stage: stage.stage,
      indication: stage.indication,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stage = this.createFromForm();
    if (stage.id !== undefined) {
      this.subscribeToSaveResponse(this.stageService.update(stage));
    } else {
      this.subscribeToSaveResponse(this.stageService.create(stage));
    }
  }

  private createFromForm(): IStage {
    return {
      ...new Stage(),
      id: this.editForm.get(['id'])!.value,
      stage: this.editForm.get(['stage'])!.value,
      indication: this.editForm.get(['indication'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStage>>): void {
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
