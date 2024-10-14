package com.qticket.payment.application.port.out;

import com.qticket.payment.domain.Concert;

public interface LoadConcertPort {

    Concert getConcert(String concertId);

}
