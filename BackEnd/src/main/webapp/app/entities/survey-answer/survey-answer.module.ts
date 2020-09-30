import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackEndSharedModule } from 'app/shared/shared.module';
import { SurveyAnswerComponent } from './survey-answer.component';
import { SurveyAnswerDetailComponent } from './survey-answer-detail.component';
import { SurveyAnswerUpdateComponent } from './survey-answer-update.component';
import { SurveyAnswerDeleteDialogComponent } from './survey-answer-delete-dialog.component';
import { surveyAnswerRoute } from './survey-answer.route';

@NgModule({
  imports: [BackEndSharedModule, RouterModule.forChild(surveyAnswerRoute)],
  declarations: [SurveyAnswerComponent, SurveyAnswerDetailComponent, SurveyAnswerUpdateComponent, SurveyAnswerDeleteDialogComponent],
  entryComponents: [SurveyAnswerDeleteDialogComponent],
})
export class BackEndSurveyAnswerModule {}
