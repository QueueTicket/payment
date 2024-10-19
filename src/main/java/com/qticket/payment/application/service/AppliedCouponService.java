package com.qticket.payment.application.service;

import com.qticket.payment.application.port.in.AppliedCouponUseCase;
import com.qticket.payment.application.port.out.AppliedBenefitPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppliedCouponService implements AppliedCouponUseCase {

    private final AppliedBenefitPort appliedBenefitPort;

    @Override
    public void appliedCoupon(String orderId) {
        appliedBenefitPort.updateBenefitApplied(orderId);
    }

}
