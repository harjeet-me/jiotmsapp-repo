package com.jio.tmsapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jio.tmsapp.web.rest.TestUtil;

public class ContainerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Container.class);
        Container container1 = new Container();
        container1.setId(1L);
        Container container2 = new Container();
        container2.setId(container1.getId());
        assertThat(container1).isEqualTo(container2);
        container2.setId(2L);
        assertThat(container1).isNotEqualTo(container2);
        container1.setId(null);
        assertThat(container1).isNotEqualTo(container2);
    }
}
