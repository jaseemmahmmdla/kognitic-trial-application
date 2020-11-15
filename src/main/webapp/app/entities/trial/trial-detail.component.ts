import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrial } from 'app/shared/model/trial.model';

@Component({
  selector: 'jhi-trial-detail',
  templateUrl: './trial-detail.component.html',
})
export class TrialDetailComponent implements OnInit {
  trial: ITrial | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trial }) => (this.trial = trial));
  }

  previousState(): void {
    window.history.back();
  }
}
