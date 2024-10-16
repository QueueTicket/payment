package com.qticket.payment.adapter.out.web.internal.concert.client;

import com.qticket.payment.domain.checkout.Ticket;

public interface ConcertMockClient {

    Ticket getTicket(String concertId);

}
