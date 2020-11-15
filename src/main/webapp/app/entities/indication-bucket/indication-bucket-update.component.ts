import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIndicationBucket, IndicationBucket } from 'app/shared/model/indication-bucket.model';
import { IndicationBucketService } from './indication-bucket.service';
import { IIndication } from 'app/shared/model/indication.model';
import { IndicationService } from 'app/entities/indication/indication.service';

@Component({
  selector: 'jhi-indication-bucket-update',
  templateUrl: './indication-bucket-update.component.html',
})
export class IndicationBucketUpdateComponent implements OnInit {
  isSaving = false;
  indications: IIndication[] = [];

  editForm = this.fb.group({
    id: [],
    indicationBucket: [],
    indication: [],
  });

  constructor(
    protected indicationBucketService: IndicationBucketService,
    protected indicationService: IndicationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicationBucket }) => {
      this.updateForm(indicationBucket);

      this.indicationService.query().subscribe((res: HttpResponse<IIndication[]>) => (this.indications = res.body || []));
    });
  }

  updateForm(indicationBucket: IIndicationBucket): void {
    this.editForm.patchValue({
      id: indicationBucket.id,
      indicationBucket: indicationBucket.indicationBucket,
      indication: indicationBucket.indication,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const indicationBucket = this.createFromForm();
    if (indicationBucket.id !== undefined) {
      this.subscribeToSaveResponse(this.indicationBucketService.update(indicationBucket));
    } else {
      this.subscribeToSaveResponse(this.indicationBucketService.create(indicationBucket));
    }
  }

  private createFromForm(): IIndicationBucket {
    return {
      ...new IndicationBucket(),
      id: this.editForm.get(['id'])!.value,
      indicationBucket: this.editForm.get(['indicationBucket'])!.value,
      indication: this.editForm.get(['indication'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndicationBucket>>): void {
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
