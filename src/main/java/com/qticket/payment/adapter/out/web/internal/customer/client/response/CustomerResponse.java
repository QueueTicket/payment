package com.qticket.payment.adapter.out.web.internal.customer.client.response;

import java.time.LocalDateTime;

public record CustomerResponse(
    Long id,
    String email,
    String name,
    String phoneNumber,
    String userRole,
    LocalDateTime birthDate
) {

}
