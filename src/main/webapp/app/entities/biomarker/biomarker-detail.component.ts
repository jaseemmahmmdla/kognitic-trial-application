import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBiomarker } from 'app/shared/model/biomarker.model';

@Component({
  selector: 'jhi-biomarker-detail',
  templateUrl: './biomarker-detail.component.html',
})
export class BiomarkerDetailComponent implements OnInit {
  biomarker: IBiomarker | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biomarker }) => (this.biomarker = biomarker));
  }

  previousState(): void {
    window.history.back();
  }
}
