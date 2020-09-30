import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackEndTestModule } from '../../../test.module';
import { SurveyQuestionUpdateComponent } from 'app/entities/survey-question/survey-question-update.component';
import { SurveyQuestionService } from 'app/entities/survey-question/survey-question.service';
import { SurveyQuestion } from 'app/shared/model/survey-question.model';

describe('Component Tests', () => {
  describe('SurveyQuestion Management Update Component', () => {
    let comp: SurveyQuestionUpdateComponent;
    let fixture: ComponentFixture<SurveyQuestionUpdateComponent>;
    let service: SurveyQuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackEndTestModule],
        declarations: [SurveyQuestionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SurveyQuestionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SurveyQuestionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SurveyQuestionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SurveyQuestion(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SurveyQuestion();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
