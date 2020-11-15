import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { TrialUpdateComponent } from 'app/entities/trial/trial-update.component';
import { TrialService } from 'app/entities/trial/trial.service';
import { Trial } from 'app/shared/model/trial.model';

describe('Component Tests', () => {
  describe('Trial Management Update Component', () => {
    let comp: TrialUpdateComponent;
    let fixture: ComponentFixture<TrialUpdateComponent>;
    let service: TrialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [TrialUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TrialUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrialUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrialService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Trial(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Trial();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
