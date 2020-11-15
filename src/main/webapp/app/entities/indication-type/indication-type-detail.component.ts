import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndicationType } from 'app/shared/model/indication-type.model';

@Component({
  selector: 'jhi-indication-type-detail',
  templateUrl: './indication-type-detail.component.html',
})
export class IndicationTypeDetailComponent implements OnInit {
  indicationType: IIndicationType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicationType }) => (this.indicationType = indicationType));
  }

  previousState(): void {
    window.history.back();
  }
}
