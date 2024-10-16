package com.qticket.payment.adapter.out.web.internal.concert.client;

import com.qticket.payment.domain.checkout.Reservation;

public interface ConcertMockClient {

    Reservation getTicket(Long customerId, String concertId);

}
