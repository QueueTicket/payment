package com.qticket.payment.adapter.out.web.internal.concert.client;

import com.qticket.payment.domain.checkout.ConcertSeat;
import com.qticket.payment.domain.checkout.Ticket;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MockConcertClient implements ConcertMockClient {

    @Override
    public Ticket getTicket(String concertId) {
        ConcertSeat concertSeat = new ConcertSeat(
            UUID.randomUUID().toString(),
            "R",
            "B",
            "10",
            new BigDecimal(115_000)
        );
        return new Ticket(
            1L,
            UUID.randomUUID().toString(),
            "아이유 콘서트",
            List.of(concertSeat)
        );
    }

}
