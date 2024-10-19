package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import com.qticket.payment.domain.approve.PaymentExecutionResult.ApproveDetails;
import com.qticket.payment.domain.checkout.Benefit;
import com.qticket.payment.domain.payment.PaymentItem;
import com.qticket.payment.domain.payment.PaymentMethod;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.PERSIST)
    private List<PaymentItemJpaEntity> paymentItems = new ArrayList<>();

    @OneToOne(mappedBy = "payment", cascade = CascadeType.PERSIST)
    private BenefitJpaEntity benefit;

    private Long customerId;
    private String orderId;
    private String orderName;
    private boolean isBenefitApplied = false;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    private boolean isCompleted = false;
    private String paymentKey;
    private int failCount = 0;
    private LocalDateTime approvedAt;

    private PaymentJpaEntity(
        List<PaymentItemJpaEntity> paymentItemEntities,
        BenefitJpaEntity benefit,
        Long customerId,
        String orderId,
        String orderName,
        PaymentMethod method
    ) {
        this.paymentItems = paymentItemEntities;
        this.benefit = benefit;
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderName = orderName;
        this.method = method;

        if (benefit != null) {
            benefit.toPayment(this);
        }
        paymentItemEntities.forEach(it -> it.toPaymentEvent(this));
    }

    public static PaymentJpaEntity of(
        Long customerId,
        String orderId,
        String orderName,
        List<PaymentItem> paymentItems,
        Benefit benefit,
        PaymentMethod method
    ) {
        // TODO 결제 항목 일급 컬렉션으로 이관
        List<PaymentItemJpaEntity> paymentItemEntities = paymentItems.stream()
            .map(PaymentItem::toEntity)
            .toList();

        BenefitJpaEntity benefitEntity = (benefit != null) ? benefit.toEntity() : null;

        return new PaymentJpaEntity(
            paymentItemEntities,
            benefitEntity,
            customerId,
            orderId,
            orderName,
            method
        );
    }

    // TODO 일급 컬렉션으로 이관
    public List<PaymentItemJpaEntity> extractChangeableProcessingOrders() {
        return paymentItems.stream()
            .filter(PaymentItemJpaEntity::isChangeableInProcessing)
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

    public void applyBenefit() {
        this.isBenefitApplied = true;
    }

}
