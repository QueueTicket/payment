package com.qticket.payment.domain.checkout;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

// TODO 정산을 위한 판매자 정보 : sellerId
public record Ticket(
    Long userId,
    String id,
    String name,
    List<ConcertSeat> concertSeats
) {

    public String seatNames() {
        return concertSeats.stream()
            .map(ConcertSeat::getSeatName)
            .collect(Collectors.joining());
    }

    public Long totalPrice() {
        return concertSeats.stream().map(ConcertSeat::price)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .longValue()
            ;
    }

}
