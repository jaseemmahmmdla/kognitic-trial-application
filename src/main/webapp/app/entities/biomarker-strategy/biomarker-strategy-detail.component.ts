import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';

@Component({
  selector: 'jhi-biomarker-strategy-detail',
  templateUrl: './biomarker-strategy-detail.component.html',
})
export class BiomarkerStrategyDetailComponent implements OnInit {
  biomarkerStrategy: IBiomarkerStrategy | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biomarkerStrategy }) => (this.biomarkerStrategy = biomarkerStrategy));
  }

  previousState(): void {
    window.history.back();
  }
}
