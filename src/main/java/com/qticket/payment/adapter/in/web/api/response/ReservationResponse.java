package com.qticket.payment.adapter.in.web.api.response;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentItemJpaEntities;
import java.util.List;

public record ReservationResponse(
    String concertId,
    List<PaymentItemResponse> paymentItems
) {

    public static ReservationResponse of(PaymentItemJpaEntities paymentItems) {
        return new ReservationResponse(
            paymentItems.concertId(),
            paymentItems.getElements()
                .stream()
                .map(PaymentItemResponse::of)
                .toList()
        );
    }

}
