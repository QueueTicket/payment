package com.qticket.payment.adapter.out.web.internal.concert.client;

import com.qticket.payment.adapter.out.web.internal.concert.client.response.ConcertSeatResponse;
import com.qticket.payment.adapter.out.web.internal.concert.client.response.PriceResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${internal.instance.name.concert}", url = "${wiremock.base.url}")
public interface ConcertAppClient {

    @GetMapping("/concert-seats/concert/{concertId}")
    List<ConcertSeatResponse> getReservedConcertSeats(@PathVariable String concertId);

    @GetMapping("/concerts/prices")
    List<PriceResponse> getTicketPrices(@RequestParam List<String> pricesId);

}
