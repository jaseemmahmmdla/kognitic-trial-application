import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { BiomarkerUpdateComponent } from 'app/entities/biomarker/biomarker-update.component';
import { BiomarkerService } from 'app/entities/biomarker/biomarker.service';
import { Biomarker } from 'app/shared/model/biomarker.model';

describe('Component Tests', () => {
  describe('Biomarker Management Update Component', () => {
    let comp: BiomarkerUpdateComponent;
    let fixture: ComponentFixture<BiomarkerUpdateComponent>;
    let service: BiomarkerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [BiomarkerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BiomarkerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BiomarkerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiomarkerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Biomarker(123);
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
        const entity = new Biomarker();
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
