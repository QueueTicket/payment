package com.qticket.payment.application.service;

import com.qticket.payment.application.port.in.AppliedBenefitUseCase;
import com.qticket.payment.application.port.out.AppliedBenefitPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppliedBenefitService implements AppliedBenefitUseCase {

    private final AppliedBenefitPort appliedBenefitPort;

    @Override
    public void appliedBenefit(String orderId) {
        appliedBenefitPort.updateBenefitApplied(orderId);
    }

}
