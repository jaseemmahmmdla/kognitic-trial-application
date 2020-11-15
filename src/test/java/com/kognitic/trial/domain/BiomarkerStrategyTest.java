package com.kognitic.trial.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kognitic.trial.web.rest.TestUtil;

public class BiomarkerStrategyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BiomarkerStrategy.class);
        BiomarkerStrategy biomarkerStrategy1 = new BiomarkerStrategy();
        biomarkerStrategy1.setId(1L);
        BiomarkerStrategy biomarkerStrategy2 = new BiomarkerStrategy();
        biomarkerStrategy2.setId(biomarkerStrategy1.getId());
        assertThat(biomarkerStrategy1).isEqualTo(biomarkerStrategy2);
        biomarkerStrategy2.setId(2L);
        assertThat(biomarkerStrategy1).isNotEqualTo(biomarkerStrategy2);
        biomarkerStrategy1.setId(null);
        assertThat(biomarkerStrategy1).isNotEqualTo(biomarkerStrategy2);
    }
}
