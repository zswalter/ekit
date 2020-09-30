import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISurveyQuestion } from 'app/shared/model/survey-question.model';

@Component({
  selector: 'jhi-survey-question-detail',
  templateUrl: './survey-question-detail.component.html',
})
export class SurveyQuestionDetailComponent implements OnInit {
  surveyQuestion: ISurveyQuestion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ surveyQuestion }) => (this.surveyQuestion = surveyQuestion));
  }

  previousState(): void {
    window.history.back();
  }
}
