import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { StageUpdateComponent } from 'app/entities/stage/stage-update.component';
import { StageService } from 'app/entities/stage/stage.service';
import { Stage } from 'app/shared/model/stage.model';

describe('Component Tests', () => {
  describe('Stage Management Update Component', () => {
    let comp: StageUpdateComponent;
    let fixture: ComponentFixture<StageUpdateComponent>;
    let service: StageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [StageUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Stage(123);
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
        const entity = new Stage();
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
