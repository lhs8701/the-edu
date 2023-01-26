const PROGRAMMING_DETAIL_CATEGORIES = [
  { title: "전체보기", id: 0 },
  { title: "백엔드", id: 1 },
  { title: "프론트엔드", id: 2 },
];
const FOREIGN_LANGUAGE_DETAIL_CATEGORIES = [
  { title: "전체보기", id: 0 },
  { title: "토익", id: 1 },
  { title: "토플", id: 2 },
  { title: "일본어", id: 3 },
];
const LICENSE_DETAIL_CATEGORIES = [
  { title: "전체보기", id: 0 },
  { title: "정보처리", id: 1 },
  { title: "전기", id: 2 },
];
const MONEY_MANAGEMENT_DETAIL_CATEGORIES = [
  { title: "전체보기", id: 0 },
  { title: "주식", id: 1 },
  { title: "펀드", id: 2 },
];

export const CATE_VALUE = [
  { big: "프로그래밍", id: 1, smallList: PROGRAMMING_DETAIL_CATEGORIES },
  { big: "자격증", id: 2, smallList: LICENSE_DETAIL_CATEGORIES },
  { big: "외국어", id: 3, smallList: FOREIGN_LANGUAGE_DETAIL_CATEGORIES },
  { big: "재테크", id: 4, smallList: MONEY_MANAGEMENT_DETAIL_CATEGORIES },
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

export const BAR_LIST = {
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

export const QUERYKEY = {
  AUTH: {},
};

export const BASE_URL = "http://218.38.127.26:8080/api";
