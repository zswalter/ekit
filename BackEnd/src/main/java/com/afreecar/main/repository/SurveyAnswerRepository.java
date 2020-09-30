package com.afreecar.main.repository;

import com.afreecar.main.domain.SurveyAnswer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SurveyAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
}
