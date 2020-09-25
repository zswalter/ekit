package com.afreecar.survey.repository;

import com.afreecar.survey.domain.TicketSurveyMap;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TicketSurveyMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketSurveyMapRepository extends JpaRepository<TicketSurveyMap, Long> {
}
