import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { IndicationDetailComponent } from 'app/entities/indication/indication-detail.component';
import { Indication } from 'app/shared/model/indication.model';

describe('Component Tests', () => {
  describe('Indication Management Detail Component', () => {
    let comp: IndicationDetailComponent;
    let fixture: ComponentFixture<IndicationDetailComponent>;
    const route = ({ data: of({ indication: new Indication(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [IndicationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IndicationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IndicationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load indication on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.indication).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
