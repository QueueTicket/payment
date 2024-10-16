package com.qticket.payment.adapter.out.web.internal.concert;

import com.qticket.payment.adapter.out.web.internal.concert.client.ConcertAppClient;
import com.qticket.payment.adapter.out.web.internal.concert.client.ConcertMockClient;
import com.qticket.payment.adapter.out.web.internal.concert.client.response.ConcertSeatResponse;
import com.qticket.payment.adapter.out.web.internal.concert.client.response.PriceResponse;
import com.qticket.payment.application.port.out.LoadConcertPort;
import com.qticket.payment.domain.checkout.ConcertSeat;
import com.qticket.payment.domain.checkout.Ticket;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertWebAdapter implements LoadConcertPort {

    @Value("${feature.toggle.use.internal.concert-app:false}")
    private boolean useInternalClient;

    private final ConcertAppClient concertAppClient;
    private final ConcertMockClient concertMockClient;

    @Override
    public Ticket getTicket(String concertId) {
        if (useInternalClient) {
            List<ConcertSeatResponse> ticket = concertAppClient.getTicket(concertId);
            List<String> pricesIds = ticket.stream().map(ConcertSeatResponse::priceId).toList();
            List<PriceResponse> ticketPrices = concertAppClient.getTicketPrice(pricesIds);

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
        return concertMockClient.getTicket(concertId);
    }

}
