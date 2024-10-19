package com.qticket.payment.application.port.out;

import com.qticket.payment.domain.checkout.Reservation;

public interface LoadConcertPort {

    Reservation getReservation(Long customerId, String concertId);

}
