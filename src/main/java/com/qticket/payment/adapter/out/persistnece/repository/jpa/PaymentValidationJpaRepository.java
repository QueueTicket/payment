package com.qticket.payment.adapter.out.persistnece.repository.jpa;

import com.qticket.payment.adapter.out.persistnece.repository.PaymentValidationRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderJpaRepository;
import com.qticket.payment.exception.persistence.AmountNotValidException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentValidationJpaRepository implements PaymentValidationRepository {

    private final PaymentOrderJpaRepository paymentOrderJpaRepository;

    @Override
    public void isValid(String orderId, Long amount) {
        BigDecimal totalAmount = paymentOrderJpaRepository.findPaymentAmount(orderId);
        if (isTotalAmountNotMatched(amount, totalAmount)) {
            throw new AmountNotValidException(orderId, amount, totalAmount);
        }
    }

    private boolean isTotalAmountNotMatched(Long amount, BigDecimal totalAmount) {
        return totalAmount.compareTo(BigDecimal.valueOf(amount)) != 0;
    }

}
