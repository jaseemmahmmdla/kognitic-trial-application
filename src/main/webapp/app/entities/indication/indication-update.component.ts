import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIndication, Indication } from 'app/shared/model/indication.model';
import { IndicationService } from './indication.service';
import { ITrial } from 'app/shared/model/trial.model';
import { TrialService } from 'app/entities/trial/trial.service';

@Component({
  selector: 'jhi-indication-update',
  templateUrl: './indication-update.component.html',
})
export class IndicationUpdateComponent implements OnInit {
  isSaving = false;
  trials: ITrial[] = [];

  editForm = this.fb.group({
    id: [],
    indication: [],
    trial: [],
  });

  constructor(
    protected indicationService: IndicationService,
    protected trialService: TrialService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indication }) => {
      this.updateForm(indication);

      this.trialService.query().subscribe((res: HttpResponse<ITrial[]>) => (this.trials = res.body || []));
    });
  }

  updateForm(indication: IIndication): void {
    this.editForm.patchValue({
      id: indication.id,
      indication: indication.indication,
      trial: indication.trial,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const indication = this.createFromForm();
    if (indication.id !== undefined) {
      this.subscribeToSaveResponse(this.indicationService.update(indication));
    } else {
      this.subscribeToSaveResponse(this.indicationService.create(indication));
    }
  }

  private createFromForm(): IIndication {
    return {
      ...new Indication(),
      id: this.editForm.get(['id'])!.value,
      indication: this.editForm.get(['indication'])!.value,
      trial: this.editForm.get(['trial'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndication>>): void {
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
