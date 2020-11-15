import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIndicationType } from 'app/shared/model/indication-type.model';
import { IndicationTypeService } from './indication-type.service';

@Component({
  templateUrl: './indication-type-delete-dialog.component.html',
})
export class IndicationTypeDeleteDialogComponent {
  indicationType?: IIndicationType;

  constructor(
    protected indicationTypeService: IndicationTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.indicationTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('indicationTypeListModification');
      this.activeModal.close();
    });
  }
}
