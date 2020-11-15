import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { IndicationBucketUpdateComponent } from 'app/entities/indication-bucket/indication-bucket-update.component';
import { IndicationBucketService } from 'app/entities/indication-bucket/indication-bucket.service';
import { IndicationBucket } from 'app/shared/model/indication-bucket.model';

describe('Component Tests', () => {
  describe('IndicationBucket Management Update Component', () => {
    let comp: IndicationBucketUpdateComponent;
    let fixture: ComponentFixture<IndicationBucketUpdateComponent>;
    let service: IndicationBucketService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [IndicationBucketUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IndicationBucketUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndicationBucketUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicationBucketService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IndicationBucket(123);
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
        const entity = new IndicationBucket();
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
