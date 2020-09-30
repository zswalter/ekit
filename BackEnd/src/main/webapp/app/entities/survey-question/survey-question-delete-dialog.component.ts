import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISurveyQuestion } from 'app/shared/model/survey-question.model';
import { SurveyQuestionService } from './survey-question.service';

@Component({
  templateUrl: './survey-question-delete-dialog.component.html',
})
export class SurveyQuestionDeleteDialogComponent {
  surveyQuestion?: ISurveyQuestion;

  constructor(
    protected surveyQuestionService: SurveyQuestionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.surveyQuestionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('surveyQuestionListModification');
      this.activeModal.close();
    });
  }
}
