package cz.jirka.test.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.jirka.test.web.rest.TestUtil;

public class ValuesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Values.class);
        Values values1 = new Values();
        values1.setId(1L);
        Values values2 = new Values();
        values2.setId(values1.getId());
        assertThat(values1).isEqualTo(values2);
        values2.setId(2L);
        assertThat(values1).isNotEqualTo(values2);
        values1.setId(null);
        assertThat(values1).isNotEqualTo(values2);
    }
}
