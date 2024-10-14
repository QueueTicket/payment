//package com.qticket.payment.domain.init;
//
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import java.time.LocalDateTime;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//
//// pg 결제 요청용
////@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Payment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private Long userId;
//    private Long orderId;
//    private Long pgResponseId;
//    private String method;
//    private String status;
//    private LocalDateTime approvedAt;
//
//}
