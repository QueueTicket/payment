package com.qticket.payment.domain;

import java.util.List;
import java.util.stream.Collectors;

// TODO 정산을 위한 판매자 정보 : sellerId
public record Concert(
    String id,
    String name,
    List<ConcertSeat> concertSeats
) {

    public String seatNames() {
        return concertSeats.stream()
            .map(ConcertSeat::getSeatName)
            .collect(Collectors.joining());
    }

}
