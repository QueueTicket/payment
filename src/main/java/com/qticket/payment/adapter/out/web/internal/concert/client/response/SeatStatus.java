package com.qticket.payment.adapter.out.web.internal.concert.client.response;

public enum SeatStatus {
    RESERVED("예약완료"),
    PAYING("결제 진행 중"),
    AVAILABLE("예약 가능");

    final String description;

    SeatStatus(String description) {
        this.description = description;
    }
}
