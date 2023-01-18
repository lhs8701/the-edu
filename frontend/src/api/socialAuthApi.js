import axios from "axios";
import { KAKAO_CLIENT_ID, KAKAO_REDIRECT_URL } from "../AuthKey";

const BASE_URL = "https://api.coinpaprika.com/v1";
const KAKAO_URL = "https://kauth.kakao.com/oauth/token";
export async function fetchCoins() {
  return axios.get(`${BASE_URL}/coins`);
}

const kakoApi = axios.create({
  baseURL: KAKAO_URL,
  headers: {
    "Content-Type": "application/json",
    "Access-Control-Allow-Credentials": true,
    "Access-Control-Allow-Origin": "*",
  },
});

export async function getKakaoAuthToken({ code }) {
  return axios.post(
    KAKAO_URL,
    {
      grant_type: "authorization_code",
      client_id: KAKAO_CLIENT_ID,
      redirect_uri: KAKAO_REDIRECT_URL,
      code: code,
    },
    {
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
    }
  );
}
