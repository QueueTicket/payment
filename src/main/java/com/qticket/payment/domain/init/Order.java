//package com.qticket.payment.domain.init;
//
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//
//// 주문 -> 결제 요청
////@Entity
////@Table(name = "orders")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private Long paymentId;
//    private Long orderId;
//    private int retryCount;
//    private BigDecimal amount;
//    private boolean isLedgerCompleted;
//    private boolean isSettlementCompleted;
//    private LocalDateTime approvedAt;
//
//}
