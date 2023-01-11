export const CATE_VALUE = [
  "전체보기",
  "프로그래밍",
  "자격증",
  "외국어",
  "재테크",
];
export const PROCESS_ACCOUNT_URL = {
  LOGIN: "account/login",
  SIGNUP: "account/signup",
  FINDID: "account/id",
  FINDPWD: "account/password",
};

export const PROCESS_MAIN_URL = {
  MAIN: "",
  MYPAGE: {
    DEFAULT: "my",
    WISH: "/wish",
    COUPON: "/coupon",
    DEAL: "/deal",
    WITHDRAW: "/withdraw",
  },
  CATEGORIES: "/category",
  COURSES: "/course",
  LOBBY: "/lobby",
  PURCHASE: "/purchase",
  ROADMAP: "/roadmap",
  EVENT: "/event",
  SEARCH: "/search",
};

export const CATEGORY_LIST = {
  isCategory: true,
  list: CATE_VALUE,
};

export const BAR_LIST = {
  isCategory: false,
  list: [
    {
      name: "나의 클래스",
      url: "",
    },
    {
      name: "찜한 클래스",
      url: "wish",
    },
    {
      name: "개인정보 수정",
      url: "revise",
    },
    {
      name: "쿠폰",
      url: "coupon",
    },
    {
      name: "구매 내역",
      url: "deal",
    },
    {
      name: "회원 탈퇴",
      url: "withdraw",
    },
  ],
};
