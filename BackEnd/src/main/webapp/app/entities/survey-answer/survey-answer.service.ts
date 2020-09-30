import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISurveyAnswer } from 'app/shared/model/survey-answer.model';

type EntityResponseType = HttpResponse<ISurveyAnswer>;
type EntityArrayResponseType = HttpResponse<ISurveyAnswer[]>;

@Injectable({ providedIn: 'root' })
export class SurveyAnswerService {
  public resourceUrl = SERVER_API_URL + 'api/survey-answers';

  constructor(protected http: HttpClient) {}

  create(surveyAnswer: ISurveyAnswer): Observable<EntityResponseType> {
    return this.http.post<ISurveyAnswer>(this.resourceUrl, surveyAnswer, { observe: 'response' });
  }

  update(surveyAnswer: ISurveyAnswer): Observable<EntityResponseType> {
    return this.http.put<ISurveyAnswer>(this.resourceUrl, surveyAnswer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISurveyAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISurveyAnswer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
