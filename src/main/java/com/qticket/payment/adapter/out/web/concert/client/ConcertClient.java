package com.qticket.payment.adapter.out.web.concert.client;

import com.qticket.payment.domain.Concert;

// TODO : FeignClient
public interface ConcertClient {

    Concert getConcert(String concertId);

}
