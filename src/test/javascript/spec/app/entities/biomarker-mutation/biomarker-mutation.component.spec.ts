import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KogniticApplicationTestModule } from '../../../test.module';
import { BiomarkerMutationComponent } from 'app/entities/biomarker-mutation/biomarker-mutation.component';
import { BiomarkerMutationService } from 'app/entities/biomarker-mutation/biomarker-mutation.service';
import { BiomarkerMutation } from 'app/shared/model/biomarker-mutation.model';

describe('Component Tests', () => {
  describe('BiomarkerMutation Management Component', () => {
    let comp: BiomarkerMutationComponent;
    let fixture: ComponentFixture<BiomarkerMutationComponent>;
    let service: BiomarkerMutationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [BiomarkerMutationComponent],
      })
        .overrideTemplate(BiomarkerMutationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BiomarkerMutationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiomarkerMutationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BiomarkerMutation(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.biomarkerMutations && comp.biomarkerMutations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
