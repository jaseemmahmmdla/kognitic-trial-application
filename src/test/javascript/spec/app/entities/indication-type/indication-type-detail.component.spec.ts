import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { IndicationTypeDetailComponent } from 'app/entities/indication-type/indication-type-detail.component';
import { IndicationType } from 'app/shared/model/indication-type.model';

describe('Component Tests', () => {
  describe('IndicationType Management Detail Component', () => {
    let comp: IndicationTypeDetailComponent;
    let fixture: ComponentFixture<IndicationTypeDetailComponent>;
    const route = ({ data: of({ indicationType: new IndicationType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [IndicationTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IndicationTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IndicationTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load indicationType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.indicationType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
