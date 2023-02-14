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

export async function postItemPurchaseApi(itemId, accessToken) {
  return await axios.post(`${PURCHASE_URL}/${itemId}`, {
    headers: {
      ACCESS: accessToken,
      "Content-Type": "application/json",
    },
  });
}
