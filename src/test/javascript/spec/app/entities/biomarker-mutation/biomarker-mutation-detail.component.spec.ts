import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { BiomarkerMutationDetailComponent } from 'app/entities/biomarker-mutation/biomarker-mutation-detail.component';
import { BiomarkerMutation } from 'app/shared/model/biomarker-mutation.model';

describe('Component Tests', () => {
  describe('BiomarkerMutation Management Detail Component', () => {
    let comp: BiomarkerMutationDetailComponent;
    let fixture: ComponentFixture<BiomarkerMutationDetailComponent>;
    const route = ({ data: of({ biomarkerMutation: new BiomarkerMutation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [BiomarkerMutationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BiomarkerMutationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BiomarkerMutationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load biomarkerMutation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.biomarkerMutation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
