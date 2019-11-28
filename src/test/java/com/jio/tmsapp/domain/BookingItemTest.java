package com.jio.tmsapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jio.tmsapp.web.rest.TestUtil;

public class BookingItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookingItem.class);
        BookingItem bookingItem1 = new BookingItem();
        bookingItem1.setId(1L);
        BookingItem bookingItem2 = new BookingItem();
        bookingItem2.setId(bookingItem1.getId());
        assertThat(bookingItem1).isEqualTo(bookingItem2);
        bookingItem2.setId(2L);
        assertThat(bookingItem1).isNotEqualTo(bookingItem2);
        bookingItem1.setId(null);
        assertThat(bookingItem1).isNotEqualTo(bookingItem2);
    }
}
