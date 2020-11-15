import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndication } from 'app/shared/model/indication.model';

@Component({
  selector: 'jhi-indication-detail',
  templateUrl: './indication-detail.component.html',
})
export class IndicationDetailComponent implements OnInit {
  indication: IIndication | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indication }) => (this.indication = indication));
  }

  previousState(): void {
    window.history.back();
  }
}
