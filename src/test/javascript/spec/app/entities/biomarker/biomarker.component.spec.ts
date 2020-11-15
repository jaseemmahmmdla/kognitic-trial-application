import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KogniticApplicationTestModule } from '../../../test.module';
import { BiomarkerComponent } from 'app/entities/biomarker/biomarker.component';
import { BiomarkerService } from 'app/entities/biomarker/biomarker.service';
import { Biomarker } from 'app/shared/model/biomarker.model';

describe('Component Tests', () => {
  describe('Biomarker Management Component', () => {
    let comp: BiomarkerComponent;
    let fixture: ComponentFixture<BiomarkerComponent>;
    let service: BiomarkerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [BiomarkerComponent],
      })
        .overrideTemplate(BiomarkerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BiomarkerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiomarkerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Biomarker(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.biomarkers && comp.biomarkers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
