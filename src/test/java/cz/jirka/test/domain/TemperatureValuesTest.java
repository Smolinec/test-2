package cz.jirka.test.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.jirka.test.web.rest.TestUtil;

public class TemperatureValuesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemperatureValues.class);
        TemperatureValues temperatureValues1 = new TemperatureValues();
        temperatureValues1.setId(1L);
        TemperatureValues temperatureValues2 = new TemperatureValues();
        temperatureValues2.setId(temperatureValues1.getId());
        assertThat(temperatureValues1).isEqualTo(temperatureValues2);
        temperatureValues2.setId(2L);
        assertThat(temperatureValues1).isNotEqualTo(temperatureValues2);
        temperatureValues1.setId(null);
        assertThat(temperatureValues1).isNotEqualTo(temperatureValues2);
    }
}
