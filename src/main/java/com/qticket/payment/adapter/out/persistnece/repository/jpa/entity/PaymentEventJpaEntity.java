package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import com.qticket.payment.domain.approve.PaymentExecutionResult.ApproveDetails;
import com.qticket.payment.domain.checkout.Coupon;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.domain.payment.PaymentOrder;
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
public class PaymentEventJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO @Cascade(CascadeType.ALL) // 결제와 결제주문의 생애주기 같은 경우 적용을 고려
    @OneToMany(mappedBy = "paymentEvent")
    private List<PaymentOrderJpaEntity> paymentOrders = new ArrayList<>();

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

    private PaymentEventJpaEntity(
        List<PaymentOrderJpaEntity> paymentOrderEntities,
        BenefitJpaEntity benefit,
        Long customerId,
        String orderId,
        String orderName,
        PaymentMethod method
    ) {
        this.paymentOrders = paymentOrderEntities;
        this.benefit = benefit;
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderName = orderName;
        this.method = method;

        if (benefit != null) {
            benefit.toPayment(this);
        }
        paymentOrderEntities.forEach(it -> it.toPaymentEvent(this));
    }

    public static PaymentEventJpaEntity of(
        Long customerId,
        String orderId,
        String orderName,
        List<PaymentOrder> paymentOrders,
        Coupon coupon,
        PaymentMethod method
    ) {
        // TODO 결제 항목 일급 컬렉션으로 이관
        List<PaymentOrderJpaEntity> paymentOrderEntities = paymentOrders.stream()
            .map(PaymentOrder::toEntity)
            .toList();

        BenefitJpaEntity benefitEntity = (coupon != null) ? coupon.toEntity() : null;

        return new PaymentEventJpaEntity(
            paymentOrderEntities,
            benefitEntity,
            customerId,
            orderId,
            orderName,
            method
        );
    }

    // TODO 일급 컬렉션으로 이관
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

    public void applyBenefit() {
        this.isBenefitApplied = true;
    }

}
