import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISurveyAnswer } from 'app/shared/model/survey-answer.model';
import { SurveyAnswerService } from './survey-answer.service';

@Component({
  templateUrl: './survey-answer-delete-dialog.component.html',
})
export class SurveyAnswerDeleteDialogComponent {
  surveyAnswer?: ISurveyAnswer;

  constructor(
    protected surveyAnswerService: SurveyAnswerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.surveyAnswerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('surveyAnswerListModification');
      this.activeModal.close();
    });
  }
}
