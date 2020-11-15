import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KogniticApplicationTestModule } from '../../../test.module';
import { IndicationComponent } from 'app/entities/indication/indication.component';
import { IndicationService } from 'app/entities/indication/indication.service';
import { Indication } from 'app/shared/model/indication.model';

describe('Component Tests', () => {
  describe('Indication Management Component', () => {
    let comp: IndicationComponent;
    let fixture: ComponentFixture<IndicationComponent>;
    let service: IndicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [IndicationComponent],
      })
        .overrideTemplate(IndicationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndicationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Indication(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.indications && comp.indications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
