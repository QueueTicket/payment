package com.qticket.payment.adapter.out.web.internal.concert.client.response;

public record ConcertSeatResponse(
    String concertSeatId,
    String seatId,
    String priceId,
    SeatStatus status
) {

}
