import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISurveyAnswer } from 'app/shared/model/survey-answer.model';
import { SurveyAnswerService } from './survey-answer.service';
import { SurveyAnswerDeleteDialogComponent } from './survey-answer-delete-dialog.component';

@Component({
  selector: 'jhi-survey-answer',
  templateUrl: './survey-answer.component.html',
})
export class SurveyAnswerComponent implements OnInit, OnDestroy {
  surveyAnswers?: ISurveyAnswer[];
  eventSubscriber?: Subscription;

  constructor(
    protected surveyAnswerService: SurveyAnswerService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.surveyAnswerService.query().subscribe((res: HttpResponse<ISurveyAnswer[]>) => (this.surveyAnswers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSurveyAnswers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISurveyAnswer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSurveyAnswers(): void {
    this.eventSubscriber = this.eventManager.subscribe('surveyAnswerListModification', () => this.loadAll());
  }

  delete(surveyAnswer: ISurveyAnswer): void {
    const modalRef = this.modalService.open(SurveyAnswerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.surveyAnswer = surveyAnswer;
  }
}
