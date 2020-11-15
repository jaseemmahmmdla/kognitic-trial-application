import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { TrialDetailComponent } from 'app/entities/trial/trial-detail.component';
import { Trial } from 'app/shared/model/trial.model';

describe('Component Tests', () => {
  describe('Trial Management Detail Component', () => {
    let comp: TrialDetailComponent;
    let fixture: ComponentFixture<TrialDetailComponent>;
    const route = ({ data: of({ trial: new Trial(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [TrialDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TrialDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrialDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load trial on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trial).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
