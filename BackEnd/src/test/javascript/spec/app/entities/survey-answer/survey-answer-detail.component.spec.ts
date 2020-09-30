import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackEndTestModule } from '../../../test.module';
import { SurveyAnswerDetailComponent } from 'app/entities/survey-answer/survey-answer-detail.component';
import { SurveyAnswer } from 'app/shared/model/survey-answer.model';

describe('Component Tests', () => {
  describe('SurveyAnswer Management Detail Component', () => {
    let comp: SurveyAnswerDetailComponent;
    let fixture: ComponentFixture<SurveyAnswerDetailComponent>;
    const route = ({ data: of({ surveyAnswer: new SurveyAnswer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackEndTestModule],
        declarations: [SurveyAnswerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SurveyAnswerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SurveyAnswerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load surveyAnswer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.surveyAnswer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
