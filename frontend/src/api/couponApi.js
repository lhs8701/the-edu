import axios from "axios";
import { API_URL } from "../static";

const COUPON_PATH = "/coupons";
const COUPON_URL = API_URL + COUPON_PATH;
const REGIST_PATH = "/register";
const UNUSED_PATH = "/unused";
const USED_PATH = "/used";

export async function registCouponApi(accessToken, couponCode) {
  return await axios.post(
    COUPON_URL + REGIST_PATH,
    { couponCode: couponCode },
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}
export async function getUnusedCouponApi(accessToken) {
  return await axios.get(COUPON_URL + UNUSED_PATH, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function getUsedCouponApi(accessToken) {
  return await axios.get(COUPON_URL + USED_PATH, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}
