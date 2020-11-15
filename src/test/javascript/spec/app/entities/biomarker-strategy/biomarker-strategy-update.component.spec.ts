import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { BiomarkerStrategyUpdateComponent } from 'app/entities/biomarker-strategy/biomarker-strategy-update.component';
import { BiomarkerStrategyService } from 'app/entities/biomarker-strategy/biomarker-strategy.service';
import { BiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';

describe('Component Tests', () => {
  describe('BiomarkerStrategy Management Update Component', () => {
    let comp: BiomarkerStrategyUpdateComponent;
    let fixture: ComponentFixture<BiomarkerStrategyUpdateComponent>;
    let service: BiomarkerStrategyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [BiomarkerStrategyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BiomarkerStrategyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BiomarkerStrategyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiomarkerStrategyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BiomarkerStrategy(123);
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
        const entity = new BiomarkerStrategy();
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
