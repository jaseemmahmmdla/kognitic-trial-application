import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITrial, Trial } from 'app/shared/model/trial.model';
import { TrialService } from './trial.service';

@Component({
  selector: 'jhi-trial-update',
  templateUrl: './trial-update.component.html',
})
export class TrialUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    trialId: [],
    trialName: [],
  });

  constructor(protected trialService: TrialService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trial }) => {
      this.updateForm(trial);
    });
  }

  updateForm(trial: ITrial): void {
    this.editForm.patchValue({
      id: trial.id,
      trialId: trial.trialId,
      trialName: trial.trialName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trial = this.createFromForm();
    if (trial.id !== undefined) {
      this.subscribeToSaveResponse(this.trialService.update(trial));
    } else {
      this.subscribeToSaveResponse(this.trialService.create(trial));
    }
  }

  private createFromForm(): ITrial {
    return {
      ...new Trial(),
      id: this.editForm.get(['id'])!.value,
      trialId: this.editForm.get(['trialId'])!.value,
      trialName: this.editForm.get(['trialName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrial>>): void {
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
}
