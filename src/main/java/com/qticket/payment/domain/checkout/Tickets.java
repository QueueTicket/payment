package com.qticket.payment.domain.checkout;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record Tickets(List<ConcertSeat> concertSeats) {

    public static Tickets of(List<ConcertSeat> list) {
        return new Tickets(list);
    }

    public String seatNames() {
        return concertSeats.stream()
            .map(ConcertSeat::getSeatName)
            .collect(Collectors.joining());
    }

    public BigDecimal totalPrice() {
        return concertSeats.stream()
            .map(ConcertSeat::price)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
