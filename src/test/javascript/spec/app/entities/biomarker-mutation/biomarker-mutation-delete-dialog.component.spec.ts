import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KogniticApplicationTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { BiomarkerMutationDeleteDialogComponent } from 'app/entities/biomarker-mutation/biomarker-mutation-delete-dialog.component';
import { BiomarkerMutationService } from 'app/entities/biomarker-mutation/biomarker-mutation.service';

describe('Component Tests', () => {
  describe('BiomarkerMutation Management Delete Component', () => {
    let comp: BiomarkerMutationDeleteDialogComponent;
    let fixture: ComponentFixture<BiomarkerMutationDeleteDialogComponent>;
    let service: BiomarkerMutationService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [BiomarkerMutationDeleteDialogComponent],
      })
        .overrideTemplate(BiomarkerMutationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BiomarkerMutationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiomarkerMutationService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
