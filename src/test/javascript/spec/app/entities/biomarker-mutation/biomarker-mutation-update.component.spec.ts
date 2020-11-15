import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { BiomarkerMutationUpdateComponent } from 'app/entities/biomarker-mutation/biomarker-mutation-update.component';
import { BiomarkerMutationService } from 'app/entities/biomarker-mutation/biomarker-mutation.service';
import { BiomarkerMutation } from 'app/shared/model/biomarker-mutation.model';

describe('Component Tests', () => {
  describe('BiomarkerMutation Management Update Component', () => {
    let comp: BiomarkerMutationUpdateComponent;
    let fixture: ComponentFixture<BiomarkerMutationUpdateComponent>;
    let service: BiomarkerMutationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [BiomarkerMutationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BiomarkerMutationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BiomarkerMutationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiomarkerMutationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BiomarkerMutation(123);
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
        const entity = new BiomarkerMutation();
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
