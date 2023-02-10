import { loadTossPayments } from "@tosspayments/payment-sdk";
import { TOSS_CLIENTKEY } from "./AuthKey";
import uuid from "react-uuid";

export function purchase(purchaseInfo, method) {
  loadTossPayments(TOSS_CLIENTKEY).then((tossPayments) => {
    tossPayments
      .requestPayment(method, {
        // 결제 수단 파라미터
        // 결제 정보 파라미터
        amount: 1,
        orderId: String(uuid()),
        orderName: "토스 티셔츠 외 2건",
        customerName: "박토스",
        successUrl: "http://localhost:3000/purchase/success/1",
        failUrl: "http://localhost:3000/purchase/fail/1",
      })
      .catch((error) => {
        if (error.code === "USER_CANCEL") {
          console.log("고객이 결제를 취소함");
        } else if (error.code === "INVALID_CARD_COMPANY") {
          console.log("유효하지 않은 카드");
        }
      });
  });
}
