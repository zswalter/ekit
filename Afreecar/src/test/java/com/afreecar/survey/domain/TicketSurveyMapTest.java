package com.afreecar.survey.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.afreecar.survey.web.rest.TestUtil;

public class TicketSurveyMapTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketSurveyMap.class);
        TicketSurveyMap ticketSurveyMap1 = new TicketSurveyMap();
        ticketSurveyMap1.setId(1L);
        TicketSurveyMap ticketSurveyMap2 = new TicketSurveyMap();
        ticketSurveyMap2.setId(ticketSurveyMap1.getId());
        assertThat(ticketSurveyMap1).isEqualTo(ticketSurveyMap2);
        ticketSurveyMap2.setId(2L);
        assertThat(ticketSurveyMap1).isNotEqualTo(ticketSurveyMap2);
        ticketSurveyMap1.setId(null);
        assertThat(ticketSurveyMap1).isNotEqualTo(ticketSurveyMap2);
    }
}
