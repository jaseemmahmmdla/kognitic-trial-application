import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KogniticApplicationTestModule } from '../../../test.module';
import { IndicationBucketComponent } from 'app/entities/indication-bucket/indication-bucket.component';
import { IndicationBucketService } from 'app/entities/indication-bucket/indication-bucket.service';
import { IndicationBucket } from 'app/shared/model/indication-bucket.model';

describe('Component Tests', () => {
  describe('IndicationBucket Management Component', () => {
    let comp: IndicationBucketComponent;
    let fixture: ComponentFixture<IndicationBucketComponent>;
    let service: IndicationBucketService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [IndicationBucketComponent],
      })
        .overrideTemplate(IndicationBucketComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndicationBucketComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicationBucketService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IndicationBucket(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.indicationBuckets && comp.indicationBuckets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
