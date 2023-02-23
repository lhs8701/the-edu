import { loadTossPayments } from "@tosspayments/payment-sdk";
import { TOSS_CLIENTKEY } from "./AuthKey";
import uuid from "react-uuid";
import { TOSS_PURCHASE_URL } from "./static";

export function purchase(purchaseInfo, method) {
  loadTossPayments(TOSS_CLIENTKEY).then((tossPayments) => {
    tossPayments
      .requestPayment(method, {
        amount: purchaseInfo.amount,
        orderId: String(uuid()),
        orderName: String(purchaseInfo.orderName),
        customerName: String(purchaseInfo.customerName),
        successUrl: `${TOSS_PURCHASE_URL}/${purchaseInfo.courseId}/${purchaseInfo.itemId}/${purchaseInfo.point}/${purchaseInfo.couponId}/success`,
        failUrl: `${TOSS_PURCHASE_URL}/${purchaseInfo.courseId}/${purchaseInfo.itemId}/${purchaseInfo.point}/${purchaseInfo.couponId}/fail`,
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