import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackEndTestModule } from '../../../test.module';
import { SurveyQuestionComponent } from 'app/entities/survey-question/survey-question.component';
import { SurveyQuestionService } from 'app/entities/survey-question/survey-question.service';
import { SurveyQuestion } from 'app/shared/model/survey-question.model';

describe('Component Tests', () => {
  describe('SurveyQuestion Management Component', () => {
    let comp: SurveyQuestionComponent;
    let fixture: ComponentFixture<SurveyQuestionComponent>;
    let service: SurveyQuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackEndTestModule],
        declarations: [SurveyQuestionComponent],
      })
        .overrideTemplate(SurveyQuestionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SurveyQuestionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SurveyQuestionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SurveyQuestion(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.surveyQuestions && comp.surveyQuestions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
