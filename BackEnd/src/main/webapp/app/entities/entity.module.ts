import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'survey-question',
        loadChildren: () => import('./survey-question/survey-question.module').then(m => m.BackEndSurveyQuestionModule),
      },
      {
        path: 'survey-answer',
        loadChildren: () => import('./survey-answer/survey-answer.module').then(m => m.BackEndSurveyAnswerModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BackEndEntityModule {}
