package com.qticket.payment.adapter.in.web.api;

import com.qticket.payment.adapter.in.web.api.response.PaymentResponse;
import com.qticket.payment.application.port.in.PaymentRetrieveUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentRetrieveController {

    private final PaymentRetrieveUseCase paymentRetrieveUseCase;

    @GetMapping("{orderId}")
    ResponseEntity<PaymentResponse> test(@PathVariable String orderId) {
        return ResponseEntity.ok(paymentRetrieveUseCase.retrievePayment(orderId));
    }

}
