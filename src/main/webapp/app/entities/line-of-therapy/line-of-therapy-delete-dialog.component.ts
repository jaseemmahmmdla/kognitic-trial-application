import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILineOfTherapy } from 'app/shared/model/line-of-therapy.model';
import { LineOfTherapyService } from './line-of-therapy.service';

@Component({
  templateUrl: './line-of-therapy-delete-dialog.component.html',
})
export class LineOfTherapyDeleteDialogComponent {
  lineOfTherapy?: ILineOfTherapy;

  constructor(
    protected lineOfTherapyService: LineOfTherapyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lineOfTherapyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lineOfTherapyListModification');
      this.activeModal.close();
    });
  }
}
