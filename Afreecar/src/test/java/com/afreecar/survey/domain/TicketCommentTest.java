package com.afreecar.survey.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.afreecar.survey.web.rest.TestUtil;

public class TicketCommentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketComment.class);
        TicketComment ticketComment1 = new TicketComment();
        ticketComment1.setId(1L);
        TicketComment ticketComment2 = new TicketComment();
        ticketComment2.setId(ticketComment1.getId());
        assertThat(ticketComment1).isEqualTo(ticketComment2);
        ticketComment2.setId(2L);
        assertThat(ticketComment1).isNotEqualTo(ticketComment2);
        ticketComment1.setId(null);
        assertThat(ticketComment1).isNotEqualTo(ticketComment2);
    }
}
