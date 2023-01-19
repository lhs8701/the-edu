import { atom, selector } from "recoil";
import { login } from "./api/authApi";

export const LoginState = atom({
  key: "LoginState",
  default: {
    state: false,
    accessToken: "",
    refreshToken: "",
  },
  storage: sessionStorage,
});

export const getLoginState = selector({
  key: "getLoginState",
  get: ({ get }) => {
    const data = get(LoginState);
    return data.state;
  },
});
