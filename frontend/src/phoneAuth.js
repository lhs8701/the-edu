import axios from "axios";

// 토스 API Gateway
const API_GATEWAY = "https://auth-test.tosspayments.com/v1/auth";

// 토스 본인인증 API 엔드포인트
const AUTH_API = `${API_GATEWAY}/personal_auth`;

// 서비스 코드
const SERVICE_CODE = "your_service_code_here";

// Access Token
const ACCESS_TOKEN = "your_access_token_here";

// 본인인증 요청 데이터
const authRequest = {
  service_code: SERVICE_CODE,
  access_token: ACCESS_TOKEN,
  name: "홍길동",
  birth_date: "19880101",
  gender: "M",
  ci: "1234567890123456789012345678901234567890",
  di: "1234567890123456789012345678901234567890",
};

// 본인인증 API 호출
axios
  .post(AUTH_API, authRequest)
  .then((response) => {
    console.log("본인인증 성공:", response.data);
  })
  .catch((error) => {
    console.error("본인인증 실패:", error.response.data);
  });
