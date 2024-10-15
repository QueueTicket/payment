package com.qticket.payment.adapter.out.web.internal.concert.client;

import com.qticket.payment.domain.checkout.Concert;

// TODO : FeignClient
public interface ConcertClient {

    Concert getConcert(String concertId);

}
