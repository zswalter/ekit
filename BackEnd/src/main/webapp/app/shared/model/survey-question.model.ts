import { QuestionType } from 'app/shared/model/enumerations/question-type.model';

export interface ISurveyQuestion {
  id?: number;
  questionText?: string;
  isActive?: boolean;
  questionType?: QuestionType;
}

export class SurveyQuestion implements ISurveyQuestion {
  constructor(public id?: number, public questionText?: string, public isActive?: boolean, public questionType?: QuestionType) {
    this.isActive = this.isActive || false;
  }
}
