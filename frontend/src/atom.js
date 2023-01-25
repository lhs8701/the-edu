import { atom, selector } from "recoil";
import { login } from "./api/authApi";

export const LoginState = atom({
  key: "LoginState",
  default: {
    state: false,
    isKakao: false,
    accessToken: "",
    refreshToken: "",
  },
  storage: sessionStorage,
});

export const KakaoAuthTokenAtom = atom({
  key: "KakaoAuthToken",
  default: "",
  storage: sessionStorage,
});

export const getLoginState = selector({
  key: "getLoginState",
  get: ({ get }) => {
    const data = get(LoginState);
    return data.state;
  },
});

export const getIsKakaoSelector = selector({
  key: "getIsKakaoSelector",
  get: ({ get }) => {
    const data = get(LoginState);
    return data.isKakao;
  },
});

export const getAccessTokenSelector = selector({
  key: "getAccessToken",
  get: ({ get }) => {
    const data = get(LoginState);
    return data.accessToken;
  },
});

export const getKakaoAuthTokenSelector = selector({
  key: "getKakaoAuthTokenSelector",
  get: ({ get }) => {
    if (get(LoginState).isKakao) {
      return get(KakaoAuthTokenAtom);
    }
    return "";
  },
});
