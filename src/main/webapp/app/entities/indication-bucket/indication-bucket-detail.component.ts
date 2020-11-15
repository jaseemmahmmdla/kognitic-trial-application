import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndicationBucket } from 'app/shared/model/indication-bucket.model';

@Component({
  selector: 'jhi-indication-bucket-detail',
  templateUrl: './indication-bucket-detail.component.html',
})
export class IndicationBucketDetailComponent implements OnInit {
  indicationBucket: IIndicationBucket | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicationBucket }) => (this.indicationBucket = indicationBucket));
  }

  previousState(): void {
    window.history.back();
  }
}
