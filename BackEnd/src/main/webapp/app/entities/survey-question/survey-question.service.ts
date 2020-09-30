import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISurveyQuestion } from 'app/shared/model/survey-question.model';

type EntityResponseType = HttpResponse<ISurveyQuestion>;
type EntityArrayResponseType = HttpResponse<ISurveyQuestion[]>;

@Injectable({ providedIn: 'root' })
export class SurveyQuestionService {
  public resourceUrl = SERVER_API_URL + 'api/survey-questions';

  constructor(protected http: HttpClient) {}

  create(surveyQuestion: ISurveyQuestion): Observable<EntityResponseType> {
    return this.http.post<ISurveyQuestion>(this.resourceUrl, surveyQuestion, { observe: 'response' });
  }

  update(surveyQuestion: ISurveyQuestion): Observable<EntityResponseType> {
    return this.http.put<ISurveyQuestion>(this.resourceUrl, surveyQuestion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISurveyQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISurveyQuestion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
