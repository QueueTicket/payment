package com.qticket.payment.adapter.out.web.internal.concert.client.response;

import java.util.UUID;

public record ConcertSeatResponse(
    UUID concertSeatId,
    UUID seatId,
    String priceId,
    SeatStatus status

) {

}
