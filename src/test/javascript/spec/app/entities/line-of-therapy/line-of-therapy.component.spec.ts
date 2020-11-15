import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KogniticApplicationTestModule } from '../../../test.module';
import { LineOfTherapyComponent } from 'app/entities/line-of-therapy/line-of-therapy.component';
import { LineOfTherapyService } from 'app/entities/line-of-therapy/line-of-therapy.service';
import { LineOfTherapy } from 'app/shared/model/line-of-therapy.model';

describe('Component Tests', () => {
  describe('LineOfTherapy Management Component', () => {
    let comp: LineOfTherapyComponent;
    let fixture: ComponentFixture<LineOfTherapyComponent>;
    let service: LineOfTherapyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [LineOfTherapyComponent],
      })
        .overrideTemplate(LineOfTherapyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineOfTherapyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineOfTherapyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LineOfTherapy(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lineOfTherapies && comp.lineOfTherapies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
