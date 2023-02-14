package joeuncamp.dabombackend.domain.order.dto;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentInfo {
    String paymentKey;
    String orderId;
    String orderName;
    String method;
    Long totalAmount;
    String status;
    Card card;
    VirtualAccount virtualAccount;
    MobilePhone mobilePhone;
    Transfer transfer;
    EasyPay easyPay;
    Failure failure;
    Discount discount;
    @Getter
    @NoArgsConstructor
    public static class Card{
        Long amount;
        String issuerCode;
        String acquirerCode;
        String number;
        Long installmentPlanMonths;
        Boolean useCardPoint;

        @Override
        public String toString() {
            return "Card{" +
                    "amount=" + amount +
                    ", issuerCode='" + issuerCode + '\'' +
                    ", acquirerCode='" + acquirerCode + '\'' +
                    ", number='" + number + '\'' +
                    ", installmentPlanMonths=" + installmentPlanMonths +
                    ", useCardPoint=" + useCardPoint +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor
    public static class VirtualAccount{
        String accountNumber;
        String bankCode;
        String refundStatus;
        String settlementStatus;

        @Override
        public String toString() {
            return "VirtualAccount{" +
                    "accountNumber='" + accountNumber + '\'' +
                    ", bankCode='" + bankCode + '\'' +
                    ", refundStatus='" + refundStatus + '\'' +
                    ", settlementStatus='" + settlementStatus + '\'' +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MobilePhone{
        String customerMobilePhone;
        String receiptUrl;

        @Override
        public String toString() {
            return "MobilePhone{" +
                    "customerMobilePhone='" + customerMobilePhone + '\'' +
                    ", receiptUrl='" + receiptUrl + '\'' +
                    '}';
        }
    }
    @Getter
    @NoArgsConstructor
    public static class Transfer{
        String bankCode;

        @Override
        public String toString() {
            return "Transfer{" +
                    "bankCode='" + bankCode + '\'' +
                    '}';
        }
    }
    @Getter
    @NoArgsConstructor
    public static class EasyPay{
        String provider;
        Long amount;
        Long discountAmount;

        @Override
        public String toString() {
            return "EasyPay{" +
                    "provider='" + provider + '\'' +
                    ", amount=" + amount +
                    ", discountAmount=" + discountAmount +
                    '}';
        }
    }
    @Getter
    @NoArgsConstructor
    public static class Failure{
        String code;
        String message;

        @Override
        public String toString() {
            return "Failure{" +
                    "code='" + code + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
    @Getter
    @NoArgsConstructor
    public static class Discount{
        Long amount;

        @Override
        public String toString() {
            return "Discount{" +
                    "amount=" + amount +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "paymentKey='" + paymentKey + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderName='" + orderName + '\'' +
                ", method='" + method + '\'' +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", card=" + card +
                ", virtualAccount=" + virtualAccount +
                ", mobilePhone=" + mobilePhone +
                ", transfer=" + transfer +
                ", easyPay=" + easyPay +
                ", failure=" + failure +
                ", discount=" + discount +
                '}';
    }
}
