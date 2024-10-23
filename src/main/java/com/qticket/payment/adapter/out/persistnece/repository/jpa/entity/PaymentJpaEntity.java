package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import com.qticket.payment.domain.approve.PaymentExecutionResult.ApproveDetails;
import com.qticket.payment.domain.checkout.Benefit;
import com.qticket.payment.domain.payment.PaymentItem;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.global.jpa.Auditable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentJpaEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PaymentItemJpaEntities paymentItems;

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
        PaymentItemJpaEntities paymentItems,
        BenefitJpaEntity benefit,
        Long customerId,
        String orderId,
        String orderName,
        PaymentMethod method
    ) {
        this.paymentItems = paymentItems;
        this.benefit = benefit;
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderName = orderName;
        this.method = method;

        if (benefit != null) {
            benefit.toPayment(this);
        }
        paymentItems.toPayment(this);
    }

    public static PaymentJpaEntity of(
        Long customerId,
        String orderId,
        String orderName,
        List<PaymentItem> paymentItems,
        Benefit benefit,
        PaymentMethod method
    ) {
        BenefitJpaEntity benefitEntity = (benefit != null) ? benefit.toEntity() : null;

        return new PaymentJpaEntity(
            PaymentItemJpaEntities.of(paymentItems),
            benefitEntity,
            customerId,
            orderId,
            orderName,
            method
        );
    }

    public PaymentItemJpaEntities extractChangeableProcessingOrders() {
        return paymentItems.extractChangeableProcessingOrders();
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

    public List<PaymentItemJpaEntity> getPaymentItemElements() {
        return paymentItems.getElements();
    }

    public BigDecimal totalAmount() {
        return paymentItems.totalAmount();
    }

    public BigDecimal paymentAmount() {
        if (isBenefitApplied) {
            return paymentItems.totalAmount().subtract(benefit.getDiscountAmount());
        }
        return paymentItems.totalAmount();
    }

}
