import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KogniticApplicationTestModule } from '../../../test.module';
import { IndicationBucketDetailComponent } from 'app/entities/indication-bucket/indication-bucket-detail.component';
import { IndicationBucket } from 'app/shared/model/indication-bucket.model';

describe('Component Tests', () => {
  describe('IndicationBucket Management Detail Component', () => {
    let comp: IndicationBucketDetailComponent;
    let fixture: ComponentFixture<IndicationBucketDetailComponent>;
    const route = ({ data: of({ indicationBucket: new IndicationBucket(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [IndicationBucketDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IndicationBucketDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IndicationBucketDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load indicationBucket on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.indicationBucket).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
