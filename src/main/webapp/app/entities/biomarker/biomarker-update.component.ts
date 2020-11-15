import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBiomarker, Biomarker } from 'app/shared/model/biomarker.model';
import { BiomarkerService } from './biomarker.service';
import { ITrial } from 'app/shared/model/trial.model';
import { TrialService } from 'app/entities/trial/trial.service';

@Component({
  selector: 'jhi-biomarker-update',
  templateUrl: './biomarker-update.component.html',
})
export class BiomarkerUpdateComponent implements OnInit {
  isSaving = false;
  trials: ITrial[] = [];

  editForm = this.fb.group({
    id: [],
    biomarker: [],
    trial: [],
  });

  constructor(
    protected biomarkerService: BiomarkerService,
    protected trialService: TrialService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biomarker }) => {
      this.updateForm(biomarker);

      this.trialService.query().subscribe((res: HttpResponse<ITrial[]>) => (this.trials = res.body || []));
    });
  }

  updateForm(biomarker: IBiomarker): void {
    this.editForm.patchValue({
      id: biomarker.id,
      biomarker: biomarker.biomarker,
      trial: biomarker.trial,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const biomarker = this.createFromForm();
    if (biomarker.id !== undefined) {
      this.subscribeToSaveResponse(this.biomarkerService.update(biomarker));
    } else {
      this.subscribeToSaveResponse(this.biomarkerService.create(biomarker));
    }
  }

  private createFromForm(): IBiomarker {
    return {
      ...new Biomarker(),
      id: this.editForm.get(['id'])!.value,
      biomarker: this.editForm.get(['biomarker'])!.value,
      trial: this.editForm.get(['trial'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBiomarker>>): void {
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

  trackById(index: number, item: ITrial): any {
    return item.id;
  }
}
