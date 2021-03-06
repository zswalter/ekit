package com.afreecar.main.repository;

import com.afreecar.main.domain.TicketSurveyMap;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TicketSurveyMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketSurveyMapRepository extends JpaRepository<TicketSurveyMap, Long> {
}
