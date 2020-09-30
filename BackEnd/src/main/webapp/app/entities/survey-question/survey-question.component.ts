import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISurveyQuestion } from 'app/shared/model/survey-question.model';
import { SurveyQuestionService } from './survey-question.service';
import { SurveyQuestionDeleteDialogComponent } from './survey-question-delete-dialog.component';

@Component({
  selector: 'jhi-survey-question',
  templateUrl: './survey-question.component.html',
})
export class SurveyQuestionComponent implements OnInit, OnDestroy {
  surveyQuestions?: ISurveyQuestion[];
  eventSubscriber?: Subscription;

  constructor(
    protected surveyQuestionService: SurveyQuestionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.surveyQuestionService.query().subscribe((res: HttpResponse<ISurveyQuestion[]>) => (this.surveyQuestions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSurveyQuestions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISurveyQuestion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSurveyQuestions(): void {
    this.eventSubscriber = this.eventManager.subscribe('surveyQuestionListModification', () => this.loadAll());
  }

  delete(surveyQuestion: ISurveyQuestion): void {
    const modalRef = this.modalService.open(SurveyQuestionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.surveyQuestion = surveyQuestion;
  }
}
