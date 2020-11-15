import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KogniticApplicationTestModule } from '../../../test.module';
import { BiomarkerStrategyComponent } from 'app/entities/biomarker-strategy/biomarker-strategy.component';
import { BiomarkerStrategyService } from 'app/entities/biomarker-strategy/biomarker-strategy.service';
import { BiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';

describe('Component Tests', () => {
  describe('BiomarkerStrategy Management Component', () => {
    let comp: BiomarkerStrategyComponent;
    let fixture: ComponentFixture<BiomarkerStrategyComponent>;
    let service: BiomarkerStrategyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [BiomarkerStrategyComponent],
      })
        .overrideTemplate(BiomarkerStrategyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BiomarkerStrategyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiomarkerStrategyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BiomarkerStrategy(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.biomarkerStrategies && comp.biomarkerStrategies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
