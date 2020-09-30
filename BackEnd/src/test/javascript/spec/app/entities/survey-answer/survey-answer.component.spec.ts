import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackEndTestModule } from '../../../test.module';
import { SurveyAnswerComponent } from 'app/entities/survey-answer/survey-answer.component';
import { SurveyAnswerService } from 'app/entities/survey-answer/survey-answer.service';
import { SurveyAnswer } from 'app/shared/model/survey-answer.model';

describe('Component Tests', () => {
  describe('SurveyAnswer Management Component', () => {
    let comp: SurveyAnswerComponent;
    let fixture: ComponentFixture<SurveyAnswerComponent>;
    let service: SurveyAnswerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackEndTestModule],
        declarations: [SurveyAnswerComponent],
      })
        .overrideTemplate(SurveyAnswerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SurveyAnswerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SurveyAnswerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SurveyAnswer(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.surveyAnswers && comp.surveyAnswers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
