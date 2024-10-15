package com.qticket.payment.adapter.out.web.internal.concert.client;

import com.qticket.payment.domain.checkout.Concert;
import com.qticket.payment.domain.checkout.ConcertSeat;
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
            "B",
            "10",
            new BigDecimal(115_000)
        );
        return new Concert(UUID.randomUUID().toString(), "아이유 콘서트", List.of(concertSeat));
    }

}
