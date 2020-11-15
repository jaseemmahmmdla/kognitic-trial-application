import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { BiomarkerStrategyDetailComponent } from 'app/entities/biomarker-strategy/biomarker-strategy-detail.component';
import { BiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';

describe('Component Tests', () => {
  describe('BiomarkerStrategy Management Detail Component', () => {
    let comp: BiomarkerStrategyDetailComponent;
    let fixture: ComponentFixture<BiomarkerStrategyDetailComponent>;
    const route = ({ data: of({ biomarkerStrategy: new BiomarkerStrategy(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [BiomarkerStrategyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BiomarkerStrategyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BiomarkerStrategyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load biomarkerStrategy on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.biomarkerStrategy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
