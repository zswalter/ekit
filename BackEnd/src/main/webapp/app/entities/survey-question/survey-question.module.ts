import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackEndSharedModule } from 'app/shared/shared.module';
import { SurveyQuestionComponent } from './survey-question.component';
import { SurveyQuestionDetailComponent } from './survey-question-detail.component';
import { SurveyQuestionUpdateComponent } from './survey-question-update.component';
import { SurveyQuestionDeleteDialogComponent } from './survey-question-delete-dialog.component';
import { surveyQuestionRoute } from './survey-question.route';

@NgModule({
  imports: [BackEndSharedModule, RouterModule.forChild(surveyQuestionRoute)],
  declarations: [
    SurveyQuestionComponent,
    SurveyQuestionDetailComponent,
    SurveyQuestionUpdateComponent,
    SurveyQuestionDeleteDialogComponent,
  ],
  entryComponents: [SurveyQuestionDeleteDialogComponent],
})
export class BackEndSurveyQuestionModule {}
