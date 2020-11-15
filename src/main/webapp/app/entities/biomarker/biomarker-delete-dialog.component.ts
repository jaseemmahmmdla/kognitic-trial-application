import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBiomarker } from 'app/shared/model/biomarker.model';
import { BiomarkerService } from './biomarker.service';

@Component({
  templateUrl: './biomarker-delete-dialog.component.html',
})
export class BiomarkerDeleteDialogComponent {
  biomarker?: IBiomarker;

  constructor(protected biomarkerService: BiomarkerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.biomarkerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('biomarkerListModification');
      this.activeModal.close();
    });
  }
}
