package com.qticket.payment.domain.checkout;

import com.qticket.payment.adapter.out.web.internal.concert.client.response.SeatGrade;
import java.math.BigDecimal;

public record ConcertSeat(
    String id,
    SeatGrade grade,
    BigDecimal price
) {

    private static final String SEAT_NAME_FORMAT = "[%s:%s]";

    public String getSeatName() {
        return SEAT_NAME_FORMAT.formatted(id, grade);
    }

}
