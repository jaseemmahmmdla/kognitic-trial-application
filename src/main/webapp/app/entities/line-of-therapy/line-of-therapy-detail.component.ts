import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILineOfTherapy } from 'app/shared/model/line-of-therapy.model';

@Component({
  selector: 'jhi-line-of-therapy-detail',
  templateUrl: './line-of-therapy-detail.component.html',
})
export class LineOfTherapyDetailComponent implements OnInit {
  lineOfTherapy: ILineOfTherapy | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lineOfTherapy }) => (this.lineOfTherapy = lineOfTherapy));
  }

  previousState(): void {
    window.history.back();
  }
}
