package com.qticket.payment.application.port.out;

import com.qticket.payment.domain.checkout.Concert;

public interface LoadConcertPort {

    Concert getConcert(String concertId);

}
