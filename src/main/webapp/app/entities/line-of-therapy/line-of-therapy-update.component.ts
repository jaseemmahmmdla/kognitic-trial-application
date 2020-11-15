import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILineOfTherapy, LineOfTherapy } from 'app/shared/model/line-of-therapy.model';
import { LineOfTherapyService } from './line-of-therapy.service';
import { IIndication } from 'app/shared/model/indication.model';
import { IndicationService } from 'app/entities/indication/indication.service';

@Component({
  selector: 'jhi-line-of-therapy-update',
  templateUrl: './line-of-therapy-update.component.html',
})
export class LineOfTherapyUpdateComponent implements OnInit {
  isSaving = false;
  indications: IIndication[] = [];

  editForm = this.fb.group({
    id: [],
    lot: [],
    indication: [],
  });

  constructor(
    protected lineOfTherapyService: LineOfTherapyService,
    protected indicationService: IndicationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lineOfTherapy }) => {
      this.updateForm(lineOfTherapy);

      this.indicationService.query().subscribe((res: HttpResponse<IIndication[]>) => (this.indications = res.body || []));
    });
  }

  updateForm(lineOfTherapy: ILineOfTherapy): void {
    this.editForm.patchValue({
      id: lineOfTherapy.id,
      lot: lineOfTherapy.lot,
      indication: lineOfTherapy.indication,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lineOfTherapy = this.createFromForm();
    if (lineOfTherapy.id !== undefined) {
      this.subscribeToSaveResponse(this.lineOfTherapyService.update(lineOfTherapy));
    } else {
      this.subscribeToSaveResponse(this.lineOfTherapyService.create(lineOfTherapy));
    }
  }

  private createFromForm(): ILineOfTherapy {
    return {
      ...new LineOfTherapy(),
      id: this.editForm.get(['id'])!.value,
      lot: this.editForm.get(['lot'])!.value,
      indication: this.editForm.get(['indication'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILineOfTherapy>>): void {
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
