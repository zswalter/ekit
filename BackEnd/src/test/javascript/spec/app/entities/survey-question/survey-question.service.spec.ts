import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SurveyQuestionService } from 'app/entities/survey-question/survey-question.service';
import { ISurveyQuestion, SurveyQuestion } from 'app/shared/model/survey-question.model';
import { QuestionType } from 'app/shared/model/enumerations/question-type.model';

describe('Service Tests', () => {
  describe('SurveyQuestion Service', () => {
    let injector: TestBed;
    let service: SurveyQuestionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISurveyQuestion;
    let expectedResult: ISurveyQuestion | ISurveyQuestion[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SurveyQuestionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SurveyQuestion(0, 'AAAAAAA', false, QuestionType.MULTI);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SurveyQuestion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SurveyQuestion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SurveyQuestion', () => {
        const returnedFromService = Object.assign(
          {
            questionText: 'BBBBBB',
            isActive: true,
            questionType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SurveyQuestion', () => {
        const returnedFromService = Object.assign(
          {
            questionText: 'BBBBBB',
            isActive: true,
            questionType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SurveyQuestion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
