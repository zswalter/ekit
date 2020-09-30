import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISurveyAnswer, SurveyAnswer } from 'app/shared/model/survey-answer.model';
import { SurveyAnswerService } from './survey-answer.service';
import { ISurveyQuestion } from 'app/shared/model/survey-question.model';
import { SurveyQuestionService } from 'app/entities/survey-question/survey-question.service';

@Component({
  selector: 'jhi-survey-answer-update',
  templateUrl: './survey-answer-update.component.html',
})
export class SurveyAnswerUpdateComponent implements OnInit {
  isSaving = false;
  surveyquestions: ISurveyQuestion[] = [];

  editForm = this.fb.group({
    id: [],
    answerText: [],
    surveyQuestion: [],
  });

  constructor(
    protected surveyAnswerService: SurveyAnswerService,
    protected surveyQuestionService: SurveyQuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ surveyAnswer }) => {
      this.updateForm(surveyAnswer);

      this.surveyQuestionService.query().subscribe((res: HttpResponse<ISurveyQuestion[]>) => (this.surveyquestions = res.body || []));
    });
  }

  updateForm(surveyAnswer: ISurveyAnswer): void {
    this.editForm.patchValue({
      id: surveyAnswer.id,
      answerText: surveyAnswer.answerText,
      surveyQuestion: surveyAnswer.surveyQuestion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const surveyAnswer = this.createFromForm();
    if (surveyAnswer.id !== undefined) {
      this.subscribeToSaveResponse(this.surveyAnswerService.update(surveyAnswer));
    } else {
      this.subscribeToSaveResponse(this.surveyAnswerService.create(surveyAnswer));
    }
  }

  private createFromForm(): ISurveyAnswer {
    return {
      ...new SurveyAnswer(),
      id: this.editForm.get(['id'])!.value,
      answerText: this.editForm.get(['answerText'])!.value,
      surveyQuestion: this.editForm.get(['surveyQuestion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISurveyAnswer>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ISurveyQuestion): any {
    return item.id;
  }
}
