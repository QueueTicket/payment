package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.DiscountPolicy;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
    name = "payment_benefit",
    uniqueConstraints = @UniqueConstraint(
        name = "UK_PAYMENT_COUPON",
        columnNames = {"payment_event_id", "coupon_id"}
    )
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentBenefitJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "payment_event_id",
        foreignKey = @ForeignKey(name = "FK_BENEFIT_TO_PAYMENT_EVENT")
    )
    private PaymentEventJpaEntity paymentEvent;

    private String couponId;

    private BigDecimal discountAmount;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

}
