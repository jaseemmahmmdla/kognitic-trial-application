import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBiomarkerStrategy, BiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';
import { BiomarkerStrategyService } from './biomarker-strategy.service';
import { IBiomarker } from 'app/shared/model/biomarker.model';
import { BiomarkerService } from 'app/entities/biomarker/biomarker.service';

@Component({
  selector: 'jhi-biomarker-strategy-update',
  templateUrl: './biomarker-strategy-update.component.html',
})
export class BiomarkerStrategyUpdateComponent implements OnInit {
  isSaving = false;
  biomarkers: IBiomarker[] = [];

  editForm = this.fb.group({
    id: [],
    biomarkerStrategy: [],
    biomarker: [],
  });

  constructor(
    protected biomarkerStrategyService: BiomarkerStrategyService,
    protected biomarkerService: BiomarkerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biomarkerStrategy }) => {
      this.updateForm(biomarkerStrategy);

      this.biomarkerService.query().subscribe((res: HttpResponse<IBiomarker[]>) => (this.biomarkers = res.body || []));
    });
  }

  updateForm(biomarkerStrategy: IBiomarkerStrategy): void {
    this.editForm.patchValue({
      id: biomarkerStrategy.id,
      biomarkerStrategy: biomarkerStrategy.biomarkerStrategy,
      biomarker: biomarkerStrategy.biomarker,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const biomarkerStrategy = this.createFromForm();
    if (biomarkerStrategy.id !== undefined) {
      this.subscribeToSaveResponse(this.biomarkerStrategyService.update(biomarkerStrategy));
    } else {
      this.subscribeToSaveResponse(this.biomarkerStrategyService.create(biomarkerStrategy));
    }
  }

  private createFromForm(): IBiomarkerStrategy {
    return {
      ...new BiomarkerStrategy(),
      id: this.editForm.get(['id'])!.value,
      biomarkerStrategy: this.editForm.get(['biomarkerStrategy'])!.value,
      biomarker: this.editForm.get(['biomarker'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBiomarkerStrategy>>): void {
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

  trackById(index: number, item: IBiomarker): any {
    return item.id;
  }
}
