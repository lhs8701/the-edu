import axios from "axios";
import { useRecoilValue } from "recoil";
import { getKakaoAuthTokenSelector } from "../atom";
import { KAKAO_CLIENT_ID, KAKAO_REDIRECT_URL, KAKAO_JSKEY } from "../AuthKey";

const BASE_URL = "http://218.38.127.26:8080/api/auth";
const KAKAO_URL = "https://kauth.kakao.com/oauth/token";
const SIGNUP_PATH = "/basic/signup";
const SIGNIN_PATH = "/basic/login";
const KAKAO_SIGNIN_PATH = "/kakao/login";
const KAKAO_LOGOUT_PATH = "/kakao/logout";

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

export async function kakaoLogin(socialToken) {
  return await axios.post(
    BASE_URL + KAKAO_SIGNIN_PATH,
    {
      socialToken: socialToken,
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
  const payload = {
    grant_type: "authorization_code",
    client_id: KAKAO_JSKEY,
    redirect_uri: KAKAO_REDIRECT_URL,
    code: code,
  };

  return await axios.post(KAKAO_URL, payload, {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
  });
}

export async function KakaoLogout(accessToken, socialToken) {
  return await axios.post(
    BASE_URL + KAKAO_LOGOUT_PATH,
    {
      socialToken: socialToken,
    },
    {
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Credentials": true,
        "Access-Control-Allow-Origin": "*",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
}
