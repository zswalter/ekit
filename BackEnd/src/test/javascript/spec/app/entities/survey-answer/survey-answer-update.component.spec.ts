import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackEndTestModule } from '../../../test.module';
import { SurveyAnswerUpdateComponent } from 'app/entities/survey-answer/survey-answer-update.component';
import { SurveyAnswerService } from 'app/entities/survey-answer/survey-answer.service';
import { SurveyAnswer } from 'app/shared/model/survey-answer.model';

describe('Component Tests', () => {
  describe('SurveyAnswer Management Update Component', () => {
    let comp: SurveyAnswerUpdateComponent;
    let fixture: ComponentFixture<SurveyAnswerUpdateComponent>;
    let service: SurveyAnswerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackEndTestModule],
        declarations: [SurveyAnswerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SurveyAnswerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SurveyAnswerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SurveyAnswerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SurveyAnswer(123);
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
        const entity = new SurveyAnswer();
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
