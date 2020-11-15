import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStage } from 'app/shared/model/stage.model';
import { StageService } from './stage.service';

@Component({
  templateUrl: './stage-delete-dialog.component.html',
})
export class StageDeleteDialogComponent {
  stage?: IStage;

  constructor(protected stageService: StageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('stageListModification');
      this.activeModal.close();
    });
  }
}
