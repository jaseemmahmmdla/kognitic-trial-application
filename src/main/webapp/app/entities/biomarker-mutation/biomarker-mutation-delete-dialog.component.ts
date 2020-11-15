import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBiomarkerMutation } from 'app/shared/model/biomarker-mutation.model';
import { BiomarkerMutationService } from './biomarker-mutation.service';

@Component({
  templateUrl: './biomarker-mutation-delete-dialog.component.html',
})
export class BiomarkerMutationDeleteDialogComponent {
  biomarkerMutation?: IBiomarkerMutation;

  constructor(
    protected biomarkerMutationService: BiomarkerMutationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.biomarkerMutationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('biomarkerMutationListModification');
      this.activeModal.close();
    });
  }
}
