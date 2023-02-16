import axios from "axios";
import { KAKAO_REDIRECT_URL, KAKAO_JSKEY } from "../AuthKey";
import { API_URL } from "../static";

const AUTH_URL = `${API_URL}/auth`;
const KAKAO_URL = "https://kauth.kakao.com/oauth/token";
const SIGNUP_PATH = "/basic/signup";
const SIGNIN_PATH = "/basic/login";
const REISSUE_PATH = "/reissue";
const BASIC_LOGOUT_PATH = "/basic/logout";
const KAKAO_SIGNIN_PATH = "/kakao/login";
const KAKAO_LOGOUT_PATH = "/kakao/logout";
const KAKAO_WITHDRAW_PATH = "/kakao/withdraw";
const BASIC_WITHDRAW_PATH = "/basic/withdraw";

export async function postTossTxId() {
  return await axios.post(
    API_URL + "/test/toss-txId",
    {},
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
}

export async function successTossCert(txid) {
  return await axios.post(
    API_URL + "/test/toss-result" + "/" + txid,
    {},
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
}

export async function kakaoWithdraw(accessToken, refreshToken, socialToken) {
  return await axios.post(
    AUTH_URL + KAKAO_WITHDRAW_PATH,
    { socialToken: socialToken },
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
        REFRESH: refreshToken,
      },
    }
  );
}

export async function basicWithdraw(accessToken, refreshToken) {
  return await axios.post(
    AUTH_URL + BASIC_WITHDRAW_PATH,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
        REFRESH: refreshToken,
      },
    }
  );
}

export async function signUp(userData) {
  return await axios.post(
    AUTH_URL + SIGNUP_PATH,
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
      },
    }
  );
}

export async function kakaoLogin(socialToken) {
  return await axios.post(
    AUTH_URL + KAKAO_SIGNIN_PATH,
    {
      socialToken: socialToken,
    },
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
}

export async function login(userData) {
  return await axios.post(
    AUTH_URL + SIGNIN_PATH,
    {
      account: userData.account,
      password: userData.password,
    },
    {
      headers: {
        "Content-Type": "application/json",
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

export async function basicLogout(accessToken, refreshToken) {
  return await axios.post(
    AUTH_URL + BASIC_LOGOUT_PATH,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
        REFRESH: refreshToken,
      },
    }
  );
}

export async function reIssue(accessToken, refreshToken) {
  return await axios.post(
    AUTH_URL + REISSUE_PATH,
    {
      accessToken: accessToken,
      refreshToken: refreshToken,
    },
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
}

export async function kakaoLogout(accessToken, socialToken, refreshToken) {
  return await axios.post(
    AUTH_URL + KAKAO_LOGOUT_PATH,
    {
      socialToken: socialToken,
    },
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
        REFRESH: refreshToken,
      },
    }
  );
}
