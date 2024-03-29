const PROGRAMMING_DETAIL_CATEGORIES = [
  { title: "백엔드", id: 1 },
  { title: "프론트엔드", id: 2 },
];
const FOREIGN_LANGUAGE_DETAIL_CATEGORIES = [
  { title: "토익", id: 1 },
  { title: "토플", id: 2 },
  { title: "일본어", id: 3 },
];
const LICENSE_DETAIL_CATEGORIES = [
  { title: "정보처리", id: 1 },
  { title: "전기", id: 2 },
];
const MONEY_MANAGEMENT_DETAIL_CATEGORIES = [
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
  EMAIL_CERTIFICATION: "account/certification",
};

export const PROCESS_MAIN_URL = {
  MAIN: "",
  MYPAGE: {
    DEFAULT: "my",
    WISH: "/wish",
    COUPON: "/coupon",
    REVISE: "/revise",
    DEAL: "/deal",
    WITHDRAW: "/withdraw",
  },
  CATEGORIES: "/category",
  COURSES: "/course",
  LOBBY: "/lobby",
  PURCHASE: "/purchase",
  PURCHASE_SUCCESS: "/purchasesuccess",
  PURCHASE_FAIL: "/purchasefail",
  ROADMAP: "/roadmap",
  EVENT: "/event",
  SEARCH: "/search",
  PLAYER: "/player",
  TERM: "/term",
  PRIVACY: "/privacyterms",
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
      name: "쿠폰 및 적립금",
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
export const ADMIN_BAR_LIST = {
  list: [
    {
      name: "회원관리",
      list: [
        {
          name: "전체 회원 조회",
          url: "/admin/users",
        },
        {
          name: "제제 목록 관리",
          url: "/admin/leaveusers",
        },
      ],
    },
    {
      name: "크리에이터 관리",
      list: [
        {
          name: "크리에이터 목록 조회",
          url: "/admin/creators",
        },
        {
          name: "크리에이터 신청 목록 조회",
          url: "/admin/creatorsrequest",
        },
      ],
    },
    {
      name: "강좌 관리",
      list: [
        {
          name: "대기 신청 강좌 관리",
          url: "/admin/enrollcourses",
        },
        {
          name: "전체 강좌 관리",
          url: "/admin/courses",
        },
        {
          name: "정지된 강좌 관리",
          url: "/admin/stopcourses",
        },
      ],
    },

    {
      name: "수익",
      list: [
        {
          name: "카데고리별 수익",
          url: "/admin/categoryprofit",
        },
        {
          name: "크리에이터별 수익",
          url: "/admin/creatorsprofit",
        },
        {
          name: "전체 수익",
          url: "/admin/profit",
        },
      ],
    },
    {
      name: "이벤트 및 공지사항",
      list: [
        {
          name: "조회 및 관리",
          url: "/admin/eventnotice",
        },

        {
          name: "업로드",
          url: "/admin/uploadevent",
        },
      ],
    },
    {
      name: "쿠폰 관리",
      list: [
        {
          name: "쿠폰 생성",
          url: "/admin/coupon",
        },
      ],
    },
  ],
};

export const CREATOR_BAR_LIST = {
  list: [
    {
      creator: [
        { name: "크리에이터 정보", url: "/creator/info" },
        {
          name: "크리에이터 신청",
          url: "/creator/register",
        },
      ],
    },
    {
      name: "수익",
      url: "/creator/profit",
    },

    {
      name: "강좌 관리",
      list: [
        {
          name: "나의 강좌",
          url: "/creator/mycourses",
        },
        {
          name: "강좌 등록 신청",
          url: "/creator/inputcourses",
        },
      ],
    },
  ],
};

export const PURCHASE_METHOD = ["카드", "계좌이체", "휴대폰"];

export const BASE_URL = "http://218.38.127.26:8080";
export const STATIC_URL = "http://the-edu.co.kr";
export const API_URL = BASE_URL + "/api";
export const PLAYER_URL = STATIC_URL + "/player";
// export const PLAYER_URL = "http://localhost:3000/player";
export const TOSS_PURCHASE_URL = STATIC_URL + "/purchase";
// export const TOSS_PURCHASE_URL = "http://localhost:3000/purchase";

export const MIN_PWD_LENGTH = 8;
export const MAX_NAME_LENGTH = 16;
export const MIN_NAME_LENGTH = 2;
export const MAX_PWD_LENGTH = 16;
