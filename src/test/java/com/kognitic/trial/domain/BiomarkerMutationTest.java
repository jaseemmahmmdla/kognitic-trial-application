package com.kognitic.trial.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kognitic.trial.web.rest.TestUtil;

public class BiomarkerMutationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BiomarkerMutation.class);
        BiomarkerMutation biomarkerMutation1 = new BiomarkerMutation();
        biomarkerMutation1.setId(1L);
        BiomarkerMutation biomarkerMutation2 = new BiomarkerMutation();
        biomarkerMutation2.setId(biomarkerMutation1.getId());
        assertThat(biomarkerMutation1).isEqualTo(biomarkerMutation2);
        biomarkerMutation2.setId(2L);
        assertThat(biomarkerMutation1).isNotEqualTo(biomarkerMutation2);
        biomarkerMutation1.setId(null);
        assertThat(biomarkerMutation1).isNotEqualTo(biomarkerMutation2);
    }
}
