import { atom, selector } from "recoil";

export const LoginState = atom({
  key: "LoginState",
  default: "",
  storage: sessionStorage,
});

export const selectLoginState = selector({
  key: "selectToDos",
  get: ({ get }) => {
    // const originalToDos = get(toDos);

    return;
  },
  set: ({ set }, newToken) => {
    // set(toDos, newToken);
  },
});
