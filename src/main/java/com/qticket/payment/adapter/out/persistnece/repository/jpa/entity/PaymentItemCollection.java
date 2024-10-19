package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import com.qticket.payment.domain.payment.PaymentItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
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
public class PaymentItemCollection {

    @OneToMany(mappedBy = "payment", cascade = CascadeType.PERSIST)
    private List<PaymentItemJpaEntity> elements = new ArrayList<>();

    public static PaymentItemCollection of(List<PaymentItem> paymentItems) {
        return new PaymentItemCollection(paymentItems.stream()
            .map(PaymentItem::toEntity)
            .toList()
        );
    }

    public void toPayment(PaymentJpaEntity paymentJpaEntity) {
        elements.forEach(it -> it.toPaymentEvent(paymentJpaEntity));
    }

    public List<PaymentItemJpaEntity> extractChangeableProcessingOrders() {
        return elements.stream()
            .filter(PaymentItemJpaEntity::isChangeableInProcessing)
            .toList();
    }

}
