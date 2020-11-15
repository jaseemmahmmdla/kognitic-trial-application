import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBiomarkerMutation } from 'app/shared/model/biomarker-mutation.model';

@Component({
  selector: 'jhi-biomarker-mutation-detail',
  templateUrl: './biomarker-mutation-detail.component.html',
})
export class BiomarkerMutationDetailComponent implements OnInit {
  biomarkerMutation: IBiomarkerMutation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biomarkerMutation }) => (this.biomarkerMutation = biomarkerMutation));
  }

  previousState(): void {
    window.history.back();
  }
}
