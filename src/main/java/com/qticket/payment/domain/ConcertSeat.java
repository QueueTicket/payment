package com.qticket.payment.domain;

import java.math.BigDecimal;

public record ConcertSeat(
    String id,
    String grade,
    String col,
    String row,
    BigDecimal price
) {

    private static final String SEAT_NAME_FORMAT = "[%s:%s:%s]";

    public String getSeatName() {
        return SEAT_NAME_FORMAT.formatted(grade, col, row);
    }

}
