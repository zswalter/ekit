package com.afreecar.survey.repository;

import com.afreecar.survey.domain.TicketComment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TicketComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, Long> {
}
