package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import com.qticket.payment.domain.payment.PaymentItem;
import com.qticket.payment.domain.payment.PaymentStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentItemJpaEntities {

    @OneToMany(mappedBy = "payment", cascade = CascadeType.PERSIST)
    private List<PaymentItemJpaEntity> elements = new ArrayList<>();

    public static PaymentItemJpaEntities of(List<PaymentItem> paymentItems) {
        return new PaymentItemJpaEntities(paymentItems.stream()
            .map(PaymentItem::toEntity)
            .toList()
        );
    }

    public void toPayment(PaymentJpaEntity paymentJpaEntity) {
        elements.forEach(it -> it.toPaymentEvent(paymentJpaEntity));
    }

    public PaymentItemJpaEntities extractChangeableProcessingOrders() {
        return new PaymentItemJpaEntities(elements.stream()
            .filter(PaymentItemJpaEntity::isChangeableInProcessing)
            .toList());
    }

    public void checkIsChangeableInProcessing() {
        elements.forEach(PaymentItemJpaEntity::checkIsChangeableInProcessing);
    }

    public void updateStatus(PaymentStatus newStatus) {
        elements.forEach(it -> it.updateStatus(newStatus));
    }

    public List<PaymentItemHistoryJpaEntity> toHistories(PaymentStatus updatedStatus, String reason) {
        return elements.stream()
            .map(it -> PaymentItemHistoryJpaEntity.of(it, updatedStatus, reason))
            .toList();
    }

    public BigDecimal totalAmount() {
        return elements.stream()
            .map(PaymentItemJpaEntity::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String concertId() {
        return elements.stream()
            .map(it -> it.getConcertId())
            .findFirst()
            .orElseThrow();
    }

}
