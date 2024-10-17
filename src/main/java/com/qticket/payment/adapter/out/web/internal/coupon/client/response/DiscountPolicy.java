package com.qticket.payment.adapter.out.web.internal.coupon.client.response;

import java.math.BigDecimal;

public enum DiscountPolicy {
    PERCENTAGE {
        @Override
        public BigDecimal calculateDiscount(
            BigDecimal originPrice,
            BigDecimal discountAmount,
            BigDecimal maxDiscountAmount
        ) {
            BigDecimal percentage = discountAmount.divide(BigDecimal.valueOf(100));
            BigDecimal calculatedDiscount = percentage.multiply(originPrice);
            return calculatedDiscount.min(maxDiscountAmount);
        }
    },
    FIXED {
        @Override
        public BigDecimal calculateDiscount(
            BigDecimal originPrice,
            BigDecimal discountAmount,
            BigDecimal maxDiscountAmount
        ) {
            return discountAmount.min(maxDiscountAmount);
        }
    };

    public abstract BigDecimal calculateDiscount(
        BigDecimal originPrice,
        BigDecimal discountAmount,
        BigDecimal maxDiscountAmount
    );
}
