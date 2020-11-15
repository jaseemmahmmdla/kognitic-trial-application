package com.kognitic.trial.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kognitic.trial.web.rest.TestUtil;

public class IndicationTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicationType.class);
        IndicationType indicationType1 = new IndicationType();
        indicationType1.setId(1L);
        IndicationType indicationType2 = new IndicationType();
        indicationType2.setId(indicationType1.getId());
        assertThat(indicationType1).isEqualTo(indicationType2);
        indicationType2.setId(2L);
        assertThat(indicationType1).isNotEqualTo(indicationType2);
        indicationType1.setId(null);
        assertThat(indicationType1).isNotEqualTo(indicationType2);
    }
}
