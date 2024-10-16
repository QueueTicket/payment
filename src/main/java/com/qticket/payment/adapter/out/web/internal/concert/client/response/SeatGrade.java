package com.qticket.payment.adapter.out.web.internal.concert.client.response;

import lombok.Getter;

@Getter
public enum SeatGrade {
    R("R석"),
    S("S석"),
    A("A석"),
    B("B석");

    final String name;

    SeatGrade(String name) {
        this.name = name;
    }
}
