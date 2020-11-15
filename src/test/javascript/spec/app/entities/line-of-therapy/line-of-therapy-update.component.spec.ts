import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { LineOfTherapyUpdateComponent } from 'app/entities/line-of-therapy/line-of-therapy-update.component';
import { LineOfTherapyService } from 'app/entities/line-of-therapy/line-of-therapy.service';
import { LineOfTherapy } from 'app/shared/model/line-of-therapy.model';

describe('Component Tests', () => {
  describe('LineOfTherapy Management Update Component', () => {
    let comp: LineOfTherapyUpdateComponent;
    let fixture: ComponentFixture<LineOfTherapyUpdateComponent>;
    let service: LineOfTherapyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [LineOfTherapyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LineOfTherapyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineOfTherapyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineOfTherapyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LineOfTherapy(123);
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
        const entity = new LineOfTherapy();
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
