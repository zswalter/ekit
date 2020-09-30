import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISurveyQuestion, SurveyQuestion } from 'app/shared/model/survey-question.model';
import { SurveyQuestionService } from './survey-question.service';

@Component({
  selector: 'jhi-survey-question-update',
  templateUrl: './survey-question-update.component.html',
})
export class SurveyQuestionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    questionText: [],
    isActive: [],
    questionType: [],
  });

  constructor(protected surveyQuestionService: SurveyQuestionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ surveyQuestion }) => {
      this.updateForm(surveyQuestion);
    });
  }

  updateForm(surveyQuestion: ISurveyQuestion): void {
    this.editForm.patchValue({
      id: surveyQuestion.id,
      questionText: surveyQuestion.questionText,
      isActive: surveyQuestion.isActive,
      questionType: surveyQuestion.questionType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const surveyQuestion = this.createFromForm();
    if (surveyQuestion.id !== undefined) {
      this.subscribeToSaveResponse(this.surveyQuestionService.update(surveyQuestion));
    } else {
      this.subscribeToSaveResponse(this.surveyQuestionService.create(surveyQuestion));
    }
  }

  private createFromForm(): ISurveyQuestion {
    return {
      ...new SurveyQuestion(),
      id: this.editForm.get(['id'])!.value,
      questionText: this.editForm.get(['questionText'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      questionType: this.editForm.get(['questionType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISurveyQuestion>>): void {
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
}
