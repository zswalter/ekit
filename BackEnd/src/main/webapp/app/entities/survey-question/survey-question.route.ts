import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISurveyQuestion, SurveyQuestion } from 'app/shared/model/survey-question.model';
import { SurveyQuestionService } from './survey-question.service';
import { SurveyQuestionComponent } from './survey-question.component';
import { SurveyQuestionDetailComponent } from './survey-question-detail.component';
import { SurveyQuestionUpdateComponent } from './survey-question-update.component';

@Injectable({ providedIn: 'root' })
export class SurveyQuestionResolve implements Resolve<ISurveyQuestion> {
  constructor(private service: SurveyQuestionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISurveyQuestion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((surveyQuestion: HttpResponse<SurveyQuestion>) => {
          if (surveyQuestion.body) {
            return of(surveyQuestion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SurveyQuestion());
  }
}

export const surveyQuestionRoute: Routes = [
  {
    path: '',
    component: SurveyQuestionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'backEndApp.surveyQuestion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SurveyQuestionDetailComponent,
    resolve: {
      surveyQuestion: SurveyQuestionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'backEndApp.surveyQuestion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SurveyQuestionUpdateComponent,
    resolve: {
      surveyQuestion: SurveyQuestionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'backEndApp.surveyQuestion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SurveyQuestionUpdateComponent,
    resolve: {
      surveyQuestion: SurveyQuestionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'backEndApp.surveyQuestion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
