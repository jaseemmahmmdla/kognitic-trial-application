package com.kognitic.trial.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kognitic.trial.web.rest.TestUtil;

public class IndicationBucketTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicationBucket.class);
        IndicationBucket indicationBucket1 = new IndicationBucket();
        indicationBucket1.setId(1L);
        IndicationBucket indicationBucket2 = new IndicationBucket();
        indicationBucket2.setId(indicationBucket1.getId());
        assertThat(indicationBucket1).isEqualTo(indicationBucket2);
        indicationBucket2.setId(2L);
        assertThat(indicationBucket1).isNotEqualTo(indicationBucket2);
        indicationBucket1.setId(null);
        assertThat(indicationBucket1).isNotEqualTo(indicationBucket2);
    }
}
