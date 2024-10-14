package com.qticket.payment.adapter.out.web.concert;

import com.qticket.payment.adapter.out.web.concert.client.ConcertClient;
import com.qticket.payment.application.port.out.LoadConcertPort;
import com.qticket.payment.domain.Concert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertWebAdapter implements LoadConcertPort {

    private final ConcertClient concertClient;

    @Override
    public Concert getConcert(String concertId) {
        return concertClient.getConcert(concertId);
    }

}
