package com.qticket.payment.adapter.out.web.concert.client;

import com.qticket.payment.domain.Concert;
import com.qticket.payment.domain.ConcertSeat;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MockConcertClient implements ConcertClient {

    @Override
    public Concert getConcert(String concertId) {
        ConcertSeat concertSeat = new ConcertSeat(
            UUID.randomUUID().toString(),
            "R",
            "A",
            "10",
            new BigDecimal(45_000)
        );
        return new Concert(UUID.randomUUID().toString(), "아이유 콘서트", List.of(concertSeat));
    }

}
