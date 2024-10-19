package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import jakarta.persistence.Entity;
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
    name = "benefit",
    uniqueConstraints = @UniqueConstraint(
        name = "UK_PAYMENT_COUPON",
        columnNames = {"payment_id", "coupon_id"}
    )
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BenefitJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "payment_id",
        foreignKey = @ForeignKey(name = "FK_BENEFIT_TO_PAYMENT")
    )
    private PaymentEventJpaEntity payment;

    private String couponId;

    private BigDecimal discountAmount;

    public BenefitJpaEntity(
        String couponId,
        BigDecimal discountAmount
    ) {
        this.couponId = couponId;
        this.discountAmount = discountAmount;
    }

    public void toPayment(PaymentEventJpaEntity paymentEventJpaEntity) {
        this.payment = paymentEventJpaEntity;
    }

}
