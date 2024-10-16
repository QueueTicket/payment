package com.qticket.payment.adapter.out.web.internal.concert.client.response;

public record PriceResponse(
    String priceId,
    Long price,
    SeatGrade seatGrade
) {

}
