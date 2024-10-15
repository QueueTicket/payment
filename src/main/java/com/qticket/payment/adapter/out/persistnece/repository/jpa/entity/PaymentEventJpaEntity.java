package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import com.qticket.payment.domain.confirm.PaymentExecutionResult.ApproveDetails;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.domain.payment.PaymentOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payment_event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEventJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO @Cascade(CascadeType.ALL) // 결제와 결제주문의 생애주기 같은 경우 적용을 고려
    @OneToMany(mappedBy = "paymentEvent")
    private List<PaymentOrderJpaEntity> paymentOrders = new ArrayList<>();

    private Long customerId;
    private String orderId;
    private String orderName;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    private String paymentKey;
    private boolean isCompleted = false;
    private LocalDateTime approvedAt;
    private int failCount;

    private PaymentEventJpaEntity(
        Long customerId,
        String orderId,
        String orderName,
        PaymentMethod method,
        List<PaymentOrderJpaEntity> paymentOrderEntities
    ) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderName = orderName;
        this.paymentOrders = paymentOrderEntities;
        this.method = method;
        paymentOrderEntities.forEach(it -> it.toPaymentEvent(this));
    }

    public static PaymentEventJpaEntity of(
        Long customerId,
        String orderId,
        String orderName,
        PaymentMethod method,
        List<PaymentOrder> paymentOrders
    ) {
        List<PaymentOrderJpaEntity> paymentOrdersEntities = paymentOrders.stream()
            .map(PaymentOrder::toEntity)
            .toList();
        return new PaymentEventJpaEntity(customerId, orderId, orderName, method, paymentOrdersEntities);
    }

    public List<PaymentOrderJpaEntity> extractChangeableProcessingOrders() {
        return paymentOrders.stream()
            .filter(PaymentOrderJpaEntity::isChangeableInProcessing)
            .toList();
    }

    public void registerPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public void updatePaymentDetails(ApproveDetails approveDetails) {
        this.orderName = approveDetails.orderName();
        this.method = approveDetails.method();
        this.approvedAt = approveDetails.approvedAt();
        this.isCompleted = true;
    }

    public void updatePaymentFailCount() {
        failCount += 1;
    }

}
