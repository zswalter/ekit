import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackEndTestModule } from '../../../test.module';
import { SurveyQuestionDetailComponent } from 'app/entities/survey-question/survey-question-detail.component';
import { SurveyQuestion } from 'app/shared/model/survey-question.model';

describe('Component Tests', () => {
  describe('SurveyQuestion Management Detail Component', () => {
    let comp: SurveyQuestionDetailComponent;
    let fixture: ComponentFixture<SurveyQuestionDetailComponent>;
    const route = ({ data: of({ surveyQuestion: new SurveyQuestion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackEndTestModule],
        declarations: [SurveyQuestionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SurveyQuestionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SurveyQuestionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load surveyQuestion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.surveyQuestion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
