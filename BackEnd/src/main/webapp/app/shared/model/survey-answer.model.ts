import { ISurveyQuestion } from 'app/shared/model/survey-question.model';

export interface ISurveyAnswer {
  id?: number;
  answerText?: string;
  surveyQuestion?: ISurveyQuestion;
}

export class SurveyAnswer implements ISurveyAnswer {
  constructor(public id?: number, public answerText?: string, public surveyQuestion?: ISurveyQuestion) {}
}
