package com.qticket.payment.adapter.out.persistnece;

import com.qticket.payment.adapter.out.persistnece.repository.PaymentRepository;
import com.qticket.payment.adapter.out.persistnece.repository.PaymentStatusUpdateRepository;
import com.qticket.payment.adapter.out.persistnece.repository.PaymentValidationRepository;
import com.qticket.payment.application.port.out.AppliedBenefitPort;
import com.qticket.payment.application.port.out.PaymentStatusUpdatePort;
import com.qticket.payment.application.port.out.PaymentValidationPort;
import com.qticket.payment.application.port.out.SavePaymentPort;
import com.qticket.payment.application.port.out.command.PaymentStatusUpdateCommand;
import com.qticket.payment.domain.payment.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements
    SavePaymentPort, PaymentStatusUpdatePort, PaymentValidationPort, AppliedBenefitPort {

    private final PaymentRepository paymentRepository;
    private final PaymentStatusUpdateRepository paymentStatusUpdateRepository;
    private final PaymentValidationRepository paymentValidationRepository;

    @Override
    public void save(PaymentEvent paymentEvent) {
        paymentRepository.save(paymentEvent);
    }

    @Override
    public void updateStatusToProcessing(String orderId, String paymentKey) {
        paymentStatusUpdateRepository.updatePaymentOrderStatusToProcessing(orderId, paymentKey);
    }

    @Override
    public void isValid(String orderId, Long amount) {
        paymentValidationRepository.isValid(orderId, amount);
    }

    @Override
    public void updatePaymentStatus(PaymentStatusUpdateCommand command) {
        paymentStatusUpdateRepository.updatePaymentOrderStatus(command);
    }

    @Override
    public void updateBenefitApplied(String orderId) {
        paymentRepository.updateBenefitApplied(orderId);
    }

}
