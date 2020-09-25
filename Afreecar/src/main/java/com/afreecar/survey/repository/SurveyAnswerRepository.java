package com.afreecar.survey.repository;

import com.afreecar.survey.domain.SurveyAnswer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SurveyAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
}
