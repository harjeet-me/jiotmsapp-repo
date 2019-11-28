package com.jio.tmsapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jio.tmsapp.web.rest.TestUtil;

public class LoadOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoadOrder.class);
        LoadOrder loadOrder1 = new LoadOrder();
        loadOrder1.setId(1L);
        LoadOrder loadOrder2 = new LoadOrder();
        loadOrder2.setId(loadOrder1.getId());
        assertThat(loadOrder1).isEqualTo(loadOrder2);
        loadOrder2.setId(2L);
        assertThat(loadOrder1).isNotEqualTo(loadOrder2);
        loadOrder1.setId(null);
        assertThat(loadOrder1).isNotEqualTo(loadOrder2);
    }
}
