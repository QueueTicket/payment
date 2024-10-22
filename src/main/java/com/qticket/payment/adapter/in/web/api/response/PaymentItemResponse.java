package com.qticket.payment.adapter.in.web.api.response;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentItemJpaEntity;
import java.math.BigDecimal;

public record PaymentItemResponse(
    String seatId,
    BigDecimal price
) {

    public static PaymentItemResponse of(PaymentItemJpaEntity paymentItemJpaEntity) {
        return new PaymentItemResponse(
            paymentItemJpaEntity.getSeatId(),
            paymentItemJpaEntity.getAmount()
        );
    }

}
