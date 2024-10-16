package com.qticket.payment.adapter.out.web.internal.concert.client;

import com.qticket.payment.adapter.out.web.internal.concert.client.response.SeatGrade;
import com.qticket.payment.domain.checkout.ConcertSeat;
import com.qticket.payment.domain.checkout.Reservation;
import com.qticket.payment.domain.checkout.Tickets;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MockConcertClient implements ConcertMockClient {

    @Override
    public Reservation getTicket(Long customerId, String concertId) {
        ConcertSeat concertSeat = new ConcertSeat(
            UUID.randomUUID().toString(),
            SeatGrade.R,
            new BigDecimal(115_000)
        );
        return new Reservation(
            customerId,
            concertId,
            Tickets.of(List.of(concertSeat)),
            concertSeat.price()
        );
    }

}
