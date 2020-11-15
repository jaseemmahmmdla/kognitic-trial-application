import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KogniticApplicationTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { IndicationBucketDeleteDialogComponent } from 'app/entities/indication-bucket/indication-bucket-delete-dialog.component';
import { IndicationBucketService } from 'app/entities/indication-bucket/indication-bucket.service';

describe('Component Tests', () => {
  describe('IndicationBucket Management Delete Component', () => {
    let comp: IndicationBucketDeleteDialogComponent;
    let fixture: ComponentFixture<IndicationBucketDeleteDialogComponent>;
    let service: IndicationBucketService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [IndicationBucketDeleteDialogComponent],
      })
        .overrideTemplate(IndicationBucketDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IndicationBucketDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicationBucketService);
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
