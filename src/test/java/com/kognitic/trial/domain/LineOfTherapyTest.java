package com.kognitic.trial.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kognitic.trial.web.rest.TestUtil;

public class LineOfTherapyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineOfTherapy.class);
        LineOfTherapy lineOfTherapy1 = new LineOfTherapy();
        lineOfTherapy1.setId(1L);
        LineOfTherapy lineOfTherapy2 = new LineOfTherapy();
        lineOfTherapy2.setId(lineOfTherapy1.getId());
        assertThat(lineOfTherapy1).isEqualTo(lineOfTherapy2);
        lineOfTherapy2.setId(2L);
        assertThat(lineOfTherapy1).isNotEqualTo(lineOfTherapy2);
        lineOfTherapy1.setId(null);
        assertThat(lineOfTherapy1).isNotEqualTo(lineOfTherapy2);
    }
}
