import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { IndicationUpdateComponent } from 'app/entities/indication/indication-update.component';
import { IndicationService } from 'app/entities/indication/indication.service';
import { Indication } from 'app/shared/model/indication.model';

describe('Component Tests', () => {
  describe('Indication Management Update Component', () => {
    let comp: IndicationUpdateComponent;
    let fixture: ComponentFixture<IndicationUpdateComponent>;
    let service: IndicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [IndicationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IndicationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndicationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Indication(123);
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
        const entity = new Indication();
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
