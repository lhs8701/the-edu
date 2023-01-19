import axios from "axios";
import { KAKAO_CLIENT_ID, KAKAO_REDIRECT_URL } from "../AuthKey";

const BASE_URL = "http://218.38.127.26:8080/api/auth";
const KAKAO_URL = "https://kauth.kakao.com/oauth/token";
const SIGNUP_PATH = "/basic/signup";
const SIGNIN_PATH = "/basic/login";

export async function signUp(userData) {
  return await axios.post(
    BASE_URL + SIGNUP_PATH,
    {
      account: userData.account,
      password: userData.password,
      name: userData.name,
      nickname: userData.nickname,
      mobile: userData.mobile,
      birthDate: userData.birthDate,
    },
    {
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Credentials": true,
        "Access-Control-Allow-Origin": "*",
      },
    }
  );
}

export async function kakaoLogin() {}

export async function login(userData) {
  return await axios.post(
    BASE_URL + SIGNIN_PATH,
    {
      account: userData.account,
      password: userData.password,
    },
    {
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Credentials": true,
        "Access-Control-Allow-Origin": "*",
      },
    }
  );
}

export async function getKakaoAuthToken(code) {
  return await axios.post(
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
