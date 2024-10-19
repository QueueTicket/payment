package com.qticket.payment.adapter.in.web.api;

import com.qticket.payment.adapter.in.web.api.request.TossPaymentConfirmRequest;
import com.qticket.payment.application.port.in.AppliedBenefitUseCase;
import com.qticket.payment.application.port.in.PaymentApproveUseCase;
import com.qticket.payment.domain.approve.PaymentApproveResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentApproveUseCase paymentApproveUseCase;
    private final AppliedBenefitUseCase appliedBenefitUseCase;

    @PostMapping("/approve")
    Mono<ResponseEntity<PaymentApproveResult>> approve(@RequestBody TossPaymentConfirmRequest request) {
        return paymentApproveUseCase.approve(request.toCommand())
            .map(it -> ResponseEntity.ok().body(it));
    }

    @PatchMapping("/applied/benefit/{orderId}")
    ResponseEntity<Void> appliedBenefit(@PathVariable String orderId) {
        appliedBenefitUseCase.appliedBenefit(orderId);
        return ResponseEntity.accepted().build();
    }

}
