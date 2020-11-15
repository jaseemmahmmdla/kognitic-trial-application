package com.kognitic.trial.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kognitic.trial.web.rest.TestUtil;

public class BiomarkerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Biomarker.class);
        Biomarker biomarker1 = new Biomarker();
        biomarker1.setId(1L);
        Biomarker biomarker2 = new Biomarker();
        biomarker2.setId(biomarker1.getId());
        assertThat(biomarker1).isEqualTo(biomarker2);
        biomarker2.setId(2L);
        assertThat(biomarker1).isNotEqualTo(biomarker2);
        biomarker1.setId(null);
        assertThat(biomarker1).isNotEqualTo(biomarker2);
    }
}
