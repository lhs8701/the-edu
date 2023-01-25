import { atom, selector } from "recoil";

export const LoginState = atom({
  key: "LoginState",
  default: {
    state: false,
    isKakao: false,
    isBasic: false,
    memberId: 0,
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

export const getIsBasicSelector = selector({
  key: "getBasicState",
  get: ({ get }) => {
    const data = get(LoginState);
    return data.isBasic;
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

export const getRefreshTokenSelector = selector({
  key: "getRefreshToken",
  get: ({ get }) => {
    const data = get(LoginState);
    return data.refreshToken;
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
