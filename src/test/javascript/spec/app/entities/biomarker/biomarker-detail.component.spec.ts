import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { BiomarkerDetailComponent } from 'app/entities/biomarker/biomarker-detail.component';
import { Biomarker } from 'app/shared/model/biomarker.model';

describe('Component Tests', () => {
  describe('Biomarker Management Detail Component', () => {
    let comp: BiomarkerDetailComponent;
    let fixture: ComponentFixture<BiomarkerDetailComponent>;
    const route = ({ data: of({ biomarker: new Biomarker(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [BiomarkerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BiomarkerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BiomarkerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load biomarker on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.biomarker).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
