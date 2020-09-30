import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISurveyAnswer, SurveyAnswer } from 'app/shared/model/survey-answer.model';
import { SurveyAnswerService } from './survey-answer.service';
import { SurveyAnswerComponent } from './survey-answer.component';
import { SurveyAnswerDetailComponent } from './survey-answer-detail.component';
import { SurveyAnswerUpdateComponent } from './survey-answer-update.component';

@Injectable({ providedIn: 'root' })
export class SurveyAnswerResolve implements Resolve<ISurveyAnswer> {
  constructor(private service: SurveyAnswerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISurveyAnswer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((surveyAnswer: HttpResponse<SurveyAnswer>) => {
          if (surveyAnswer.body) {
            return of(surveyAnswer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SurveyAnswer());
  }
}

export const surveyAnswerRoute: Routes = [
  {
    path: '',
    component: SurveyAnswerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'backEndApp.surveyAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SurveyAnswerDetailComponent,
    resolve: {
      surveyAnswer: SurveyAnswerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'backEndApp.surveyAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SurveyAnswerUpdateComponent,
    resolve: {
      surveyAnswer: SurveyAnswerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'backEndApp.surveyAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SurveyAnswerUpdateComponent,
    resolve: {
      surveyAnswer: SurveyAnswerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'backEndApp.surveyAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
