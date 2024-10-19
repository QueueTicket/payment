package com.qticket.payment.adapter.out.web.internal.concert;

import com.qticket.payment.adapter.out.web.internal.concert.client.ConcertAppClient;
import com.qticket.payment.adapter.out.web.internal.concert.client.ConcertMockClient;
import com.qticket.payment.adapter.out.web.internal.concert.client.response.ConcertSeatResponse;
import com.qticket.payment.adapter.out.web.internal.concert.client.response.PriceResponse;
import com.qticket.payment.application.port.out.LoadConcertPort;
import com.qticket.payment.domain.checkout.Reservation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertWebAdapter implements LoadConcertPort {

    private final ConcertAppClient concertAppClient;
    private final ConcertMockClient concertMockClient;

    @Value("${feature.toggle.use.internal.concert-app:false}")
    private boolean useInternalClient;

    // TODO 이름 변경 getReservation
    @Override
    public Reservation getTicket(Long customerId, String concertId) {
        if (useInternalClient) {
            List<ConcertSeatResponse> reservationSeat = concertAppClient.getReservedConcertSeats(concertId);
            List<String> pricesIds = extractPriceIds(reservationSeat);
            List<PriceResponse> ticketPrices = concertAppClient.getTicketPrices(pricesIds);

            return Reservation.of(customerId, concertId, reservationSeat, ticketPrices);
        }
        return concertMockClient.getTicket(customerId, concertId);
    }

    private List<String> extractPriceIds(List<ConcertSeatResponse> reservationSeat) {
        return reservationSeat.stream()
            .map(ConcertSeatResponse::priceId).toList();
    }

}
