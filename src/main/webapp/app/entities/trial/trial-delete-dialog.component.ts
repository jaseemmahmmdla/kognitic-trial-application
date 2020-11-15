import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrial } from 'app/shared/model/trial.model';
import { TrialService } from './trial.service';

@Component({
  templateUrl: './trial-delete-dialog.component.html',
})
export class TrialDeleteDialogComponent {
  trial?: ITrial;

  constructor(protected trialService: TrialService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trialService.delete(id).subscribe(() => {
      this.eventManager.broadcast('trialListModification');
      this.activeModal.close();
    });
  }
}
