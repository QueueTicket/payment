//package com.qticket.payment.adapter.out.web.external.payment.toss.response;
//
//import java.util.List;
//import java.util.Optional;
//
//public class TossPaymentConfirmResponse {
//
//    public static record Payment(
//        String version,
//        String paymentKey, //
//        String type, //
//        String orderId, //
//        String orderName, //
//        String mId,
//        String currency,
//        Optional<String> method, //
//        double totalAmount, //
//        double balanceAmount,
//        String status, //
//        String requestedAt, //
//        Optional<String> approvedAt, //
//        boolean useEscrow,
//        Optional<String> lastTransactionKey,
//        double suppliedAmount,
//        double vat,
//        boolean cultureExpense,
//        double taxFreeAmount,
//        int taxExemptionAmount,
//        List<Cancel> cancels, //
//        Optional<Card> card,
//        Optional<VirtualAccount> virtualAccount,
//        Optional<CashReceipt> cashReceipt,
//        Optional<EasyPay> easyPay, //
//        Optional<GiftCertificate> giftCertificate,
//        Optional<Transfer> transfer,
//        Optional<Metadata> metadata,
//        Optional<Failure> failure
//    ) {
//
//    }
//
//    public record Cancel(
//        double cancelAmount,
//        String cancelReason,
//        double taxFreeAmount,
//        int taxExemptionAmount,
//        double refundableAmount,
//        double easyPayDiscountAmount,
//        String canceledAt,
//        String transactionKey,
//        Optional<String> receiptKey,
//        String cancelStatus,
//        Optional<String> cancelRequestId,
//        boolean isPartialCancelable,
//        String acquireStatus
//    ) {
//
//    }
//
//    public record Card(
//        double amount,
//        String issuerCode,
//        Optional<String> acquirerCode,
//        String number,
//        int installmentPlanMonths,
//        String approveNo,
//        boolean useCardPoint,
//        String cardType,
//        String ownerType,
//        String acquireStatus,
//        boolean isInterestFree,
//        Optional<String> interestPayer
//    ) {
//
//    }
//
//    public record VirtualAccount(
//        String accountType,
//        String accountNumber,
//        String bankCode,
//        String customerName,
//        String dueDate,
//        String refundStatus,
//        boolean expired,
//        String settlementStatus,
//        Optional<GiftCertificate> giftCertificate
//    ) {
//
//    }
//
//    public record CashReceipt(
//        String type,
//        String receiptKey,
//        String issueNumber,
//        String receiptUrl,
//        double amount,
//        double taxFreeAmount,
//        List<CashReceiptHistory> cashReceipts
//    ) {
//
//    }
//
//    public record CashReceiptHistory(
//        String receiptKey,
//        String orderId,
//        String orderName,
//        String type,
//        String issueNumber,
//        String receiptUrl,
//        String businessNumber,
//        String transactionType,
//        double amount,
//        double taxFreeAmount,
//        String issueStatus,
//        Optional<Failure> failure,
//        String customerIdentityNumber,
//        String requestedAt
//    ) {
//
//    }
//
//    public record EasyPay(
//        String provider,
//        double amount,
//        double discountAmount,
//        String country
//    ) {
//
//    }
//
//    public record GiftCertificate(
//        String approveNo,
//        String settlementStatus
//    ) {
//
//    }
//
//    public record Transfer(
//        String bankCode,
//        String settlementStatus
//    ) {
//
//    }
//
//    public record Metadata(
//        List<KeyValue> keyValues
//    ) {
//
//    }
//
//    public record KeyValue(
//        String key,
//        String value
//    ) {
//
//    }
//
//    public record Failure(
//        String code,
//        String message
//    ) {
//
//    }
//
//}
