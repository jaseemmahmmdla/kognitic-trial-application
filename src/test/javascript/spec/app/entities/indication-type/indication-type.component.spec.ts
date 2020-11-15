import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KogniticApplicationTestModule } from '../../../test.module';
import { IndicationTypeComponent } from 'app/entities/indication-type/indication-type.component';
import { IndicationTypeService } from 'app/entities/indication-type/indication-type.service';
import { IndicationType } from 'app/shared/model/indication-type.model';

describe('Component Tests', () => {
  describe('IndicationType Management Component', () => {
    let comp: IndicationTypeComponent;
    let fixture: ComponentFixture<IndicationTypeComponent>;
    let service: IndicationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [IndicationTypeComponent],
      })
        .overrideTemplate(IndicationTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndicationTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicationTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IndicationType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.indicationTypes && comp.indicationTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
