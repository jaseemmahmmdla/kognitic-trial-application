import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIndication } from 'app/shared/model/indication.model';
import { IndicationService } from './indication.service';

@Component({
  templateUrl: './indication-delete-dialog.component.html',
})
export class IndicationDeleteDialogComponent {
  indication?: IIndication;

  constructor(
    protected indicationService: IndicationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.indicationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('indicationListModification');
      this.activeModal.close();
    });
  }
}
