import { loadTossPayments } from "@tosspayments/payment-sdk";
import { TOSS_CLIENTKEY } from "./AuthKey";
import uuid from "react-uuid";

export function purchase(purchaseInfo, method) {
  console.log();
  loadTossPayments(TOSS_CLIENTKEY).then((tossPayments) => {
    tossPayments
      .requestPayment(method, {
        // 결제 수단 파라미터
        // 결제 정보 파라미터
        amount: purchaseInfo.amount,
        orderId: String(uuid()),
        orderName: String(purchaseInfo.orderName),
        customerName: String(purchaseInfo.customerName),
        successUrl: `http://localhost:3000/purchase/${purchaseInfo.courseId}/${purchaseInfo.itemId}/${purchaseInfo.point}/${purchaseInfo.couponId}/success`,
        failUrl: `http://localhost:3000/purchase/${purchaseInfo.courseId}/${purchaseInfo.itemId}/${purchaseInfo.point}/${purchaseInfo.couponId}/fail`,
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
