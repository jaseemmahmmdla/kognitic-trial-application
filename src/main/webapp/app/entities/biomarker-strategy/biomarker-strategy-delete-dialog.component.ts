import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';
import { BiomarkerStrategyService } from './biomarker-strategy.service';

@Component({
  templateUrl: './biomarker-strategy-delete-dialog.component.html',
})
export class BiomarkerStrategyDeleteDialogComponent {
  biomarkerStrategy?: IBiomarkerStrategy;

  constructor(
    protected biomarkerStrategyService: BiomarkerStrategyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.biomarkerStrategyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('biomarkerStrategyListModification');
      this.activeModal.close();
    });
  }
}
