import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { LineOfTherapyDetailComponent } from 'app/entities/line-of-therapy/line-of-therapy-detail.component';
import { LineOfTherapy } from 'app/shared/model/line-of-therapy.model';

describe('Component Tests', () => {
  describe('LineOfTherapy Management Detail Component', () => {
    let comp: LineOfTherapyDetailComponent;
    let fixture: ComponentFixture<LineOfTherapyDetailComponent>;
    const route = ({ data: of({ lineOfTherapy: new LineOfTherapy(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [LineOfTherapyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LineOfTherapyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LineOfTherapyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lineOfTherapy on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lineOfTherapy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
