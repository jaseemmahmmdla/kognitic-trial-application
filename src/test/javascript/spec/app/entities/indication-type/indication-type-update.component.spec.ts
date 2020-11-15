import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { IndicationTypeUpdateComponent } from 'app/entities/indication-type/indication-type-update.component';
import { IndicationTypeService } from 'app/entities/indication-type/indication-type.service';
import { IndicationType } from 'app/shared/model/indication-type.model';

describe('Component Tests', () => {
  describe('IndicationType Management Update Component', () => {
    let comp: IndicationTypeUpdateComponent;
    let fixture: ComponentFixture<IndicationTypeUpdateComponent>;
    let service: IndicationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [IndicationTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IndicationTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndicationTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicationTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IndicationType(123);
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
        const entity = new IndicationType();
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
