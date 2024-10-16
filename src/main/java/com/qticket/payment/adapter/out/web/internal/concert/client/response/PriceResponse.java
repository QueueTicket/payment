package com.qticket.payment.adapter.out.web.internal.concert.client.response;

import java.util.UUID;

public record PriceResponse(
    UUID priceId,
    Integer price,
    SeatGrade seatGrade
) {

}
