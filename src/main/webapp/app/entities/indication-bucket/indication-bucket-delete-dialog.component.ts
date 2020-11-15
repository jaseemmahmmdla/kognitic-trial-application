import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIndicationBucket } from 'app/shared/model/indication-bucket.model';
import { IndicationBucketService } from './indication-bucket.service';

@Component({
  templateUrl: './indication-bucket-delete-dialog.component.html',
})
export class IndicationBucketDeleteDialogComponent {
  indicationBucket?: IIndicationBucket;

  constructor(
    protected indicationBucketService: IndicationBucketService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.indicationBucketService.delete(id).subscribe(() => {
      this.eventManager.broadcast('indicationBucketListModification');
      this.activeModal.close();
    });
  }
}
