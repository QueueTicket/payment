package com.qticket.payment.application.port.out;

import com.qticket.payment.domain.checkout.Ticket;

public interface LoadConcertPort {

    Ticket getTicket(String concertId);

}
