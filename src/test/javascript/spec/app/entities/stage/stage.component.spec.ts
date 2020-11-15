import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KogniticApplicationTestModule } from '../../../test.module';
import { StageComponent } from 'app/entities/stage/stage.component';
import { StageService } from 'app/entities/stage/stage.service';
import { Stage } from 'app/shared/model/stage.model';

describe('Component Tests', () => {
  describe('Stage Management Component', () => {
    let comp: StageComponent;
    let fixture: ComponentFixture<StageComponent>;
    let service: StageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KogniticApplicationTestModule],
        declarations: [StageComponent],
      })
        .overrideTemplate(StageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Stage(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.stages && comp.stages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
