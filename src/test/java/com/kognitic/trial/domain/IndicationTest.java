package com.kognitic.trial.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kognitic.trial.web.rest.TestUtil;

public class IndicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indication.class);
        Indication indication1 = new Indication();
        indication1.setId(1L);
        Indication indication2 = new Indication();
        indication2.setId(indication1.getId());
        assertThat(indication1).isEqualTo(indication2);
        indication2.setId(2L);
        assertThat(indication1).isNotEqualTo(indication2);
        indication1.setId(null);
        assertThat(indication1).isNotEqualTo(indication2);
    }
}
