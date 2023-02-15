import axios from "axios";
import { API_URL } from "../static";

const PURCHASE_URL = `${API_URL}/purchase/items`;

export async function getItemReceiptApi(itemId, accessToken) {
  return await axios.get(`${PURCHASE_URL}/${itemId}`, {
    headers: {
      ACCESS: accessToken,
      "Content-Type": "application/json",
    },
  });
}

export async function postItemPurchaseApi(itemId, accessToken, data) {
  return await axios.post(
    `${PURCHASE_URL}/${itemId}`,
    {
      requestDto: {
        couponId: data.couponId,
        point: data.point,
      },
      tossPayRequest: {
        tossPaymentKey: data.key,
        tossOrderId: data.tossId,
        tossAmount: Number(data.amount),
      },
    },
    {
      headers: {
        ACCESS: accessToken,
        "Content-Type": "application/json",
      },
    }
  );
}
