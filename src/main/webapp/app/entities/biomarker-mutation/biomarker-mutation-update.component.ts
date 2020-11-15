import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBiomarkerMutation, BiomarkerMutation } from 'app/shared/model/biomarker-mutation.model';
import { BiomarkerMutationService } from './biomarker-mutation.service';
import { IBiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';
import { BiomarkerStrategyService } from 'app/entities/biomarker-strategy/biomarker-strategy.service';

@Component({
  selector: 'jhi-biomarker-mutation-update',
  templateUrl: './biomarker-mutation-update.component.html',
})
export class BiomarkerMutationUpdateComponent implements OnInit {
  isSaving = false;
  biomarkerstrategies: IBiomarkerStrategy[] = [];

  editForm = this.fb.group({
    id: [],
    biomarkerMutation: [],
    biomarkerStrategy: [],
  });

  constructor(
    protected biomarkerMutationService: BiomarkerMutationService,
    protected biomarkerStrategyService: BiomarkerStrategyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biomarkerMutation }) => {
      this.updateForm(biomarkerMutation);

      this.biomarkerStrategyService
        .query()
        .subscribe((res: HttpResponse<IBiomarkerStrategy[]>) => (this.biomarkerstrategies = res.body || []));
    });
  }

  updateForm(biomarkerMutation: IBiomarkerMutation): void {
    this.editForm.patchValue({
      id: biomarkerMutation.id,
      biomarkerMutation: biomarkerMutation.biomarkerMutation,
      biomarkerStrategy: biomarkerMutation.biomarkerStrategy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const biomarkerMutation = this.createFromForm();
    if (biomarkerMutation.id !== undefined) {
      this.subscribeToSaveResponse(this.biomarkerMutationService.update(biomarkerMutation));
    } else {
      this.subscribeToSaveResponse(this.biomarkerMutationService.create(biomarkerMutation));
    }
  }

  private createFromForm(): IBiomarkerMutation {
    return {
      ...new BiomarkerMutation(),
      id: this.editForm.get(['id'])!.value,
      biomarkerMutation: this.editForm.get(['biomarkerMutation'])!.value,
      biomarkerStrategy: this.editForm.get(['biomarkerStrategy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBiomarkerMutation>>): void {
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

  trackById(index: number, item: IBiomarkerStrategy): any {
    return item.id;
  }
}
