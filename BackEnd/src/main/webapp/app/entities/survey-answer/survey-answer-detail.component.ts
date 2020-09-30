import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISurveyAnswer } from 'app/shared/model/survey-answer.model';

@Component({
  selector: 'jhi-survey-answer-detail',
  templateUrl: './survey-answer-detail.component.html',
})
export class SurveyAnswerDetailComponent implements OnInit {
  surveyAnswer: ISurveyAnswer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ surveyAnswer }) => (this.surveyAnswer = surveyAnswer));
  }

  previousState(): void {
    window.history.back();
  }
}
