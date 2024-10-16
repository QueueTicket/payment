package com.qticket.payment.domain.checkout;

import com.qticket.payment.adapter.out.web.internal.concert.client.response.ConcertSeatResponse;
import com.qticket.payment.adapter.out.web.internal.concert.client.response.PriceResponse;
import java.math.BigDecimal;
import java.util.List;

public record Reservation(
    Long customerId,
    String concertId,
    Tickets tickets,
    BigDecimal totalPrice
) {

    public String seatNames() {
        return tickets.seatNames();
    }

    public List<ConcertSeat> concertSeats() {
        return tickets.concertSeats();
    }

    public static Reservation of(
        Long customerId,
        String concertId,
        List<ConcertSeatResponse> reservationSeats,
        List<PriceResponse> ticketPrices
    ) {
        Tickets tickets = createTickets(reservationSeats, ticketPrices);
        return new Reservation(customerId, concertId, tickets, tickets.totalPrice());
    }

    private static Tickets createTickets(
        List<ConcertSeatResponse> reservationSeats,
        List<PriceResponse> ticketPrices
    ) {
        return Tickets.of(reservationSeats.stream()
            .map(seatResponse -> {
                PriceResponse matchingPrice = findMatchingPrice(ticketPrices, seatResponse.priceId());
                return createConcertSeat(seatResponse, matchingPrice);
            }).toList());
    }

    private static PriceResponse findMatchingPrice(List<PriceResponse> ticketPrices, String priceId) {
        return ticketPrices.stream()
            .filter(price -> price.priceId().equals(priceId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(priceId));
    }

    private static ConcertSeat createConcertSeat(
        ConcertSeatResponse concertSeatResponse,
        PriceResponse priceResponse
    ) {
        return new ConcertSeat(
            concertSeatResponse.concertSeatId(),
            priceResponse.seatGrade(),
            BigDecimal.valueOf(priceResponse.price())
        );
    }

}
