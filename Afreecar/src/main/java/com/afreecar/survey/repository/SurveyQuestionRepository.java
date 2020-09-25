package com.afreecar.survey.repository;

import com.afreecar.survey.domain.SurveyQuestion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SurveyQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {
}
