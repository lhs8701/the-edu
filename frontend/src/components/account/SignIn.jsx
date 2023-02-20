import { useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { PROCESS_ACCOUNT_URL } from "../../static";
import {
  AccountBtn,
  AccountForm,
  AccountInput,
  AccountSmallBtn,
  AccountTitle,
  AccountWrapper,
  ErrorMessage,
  InputBox,
  InputLabel,
} from "../../style/AccountComponentCss";
import { useForm } from "react-hook-form";
import { KAKAO_CLIENT_ID, KAKAO_REDIRECT_URL } from "../../AuthKey";
import { login } from "../../api/authApi";
import { useRecoilState, useRecoilValue } from "recoil";
import { getLoginState, LoginState } from "../../atom";
import kakaoImg from "../../images/kakao_login_medium_wide.png";

const LoginLinkBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 34px;
`;

const BtnSignUpBox = styled(LoginLinkBox)`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const AnyLink = styled(Link)`
  font-size: 0.9rem;
  font-weight: var(--weight-thin);
  text-decoration: none;
  color: var(--color-gray);
  margin-left: 20px;
  &:hover {
    color: var(--color-black);
    font-weight: var(--weight-point);
  }
  &:active {
    transform: scale(0.9);
  }
`;

const ImgDiv = styled.div`
  display: flex;
  margin: 0 auto;
  width: 90%;
  align-items: center;
  justify-content: center;
  background-color: #fee501;
  border-radius: var(--size-border-radius);
`;

export default function SignIn() {
  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URL}&response_type=code`;
  const [isLoggedIn, setIsLoggedIn] = useRecoilState(LoginState);
  const [isID, setIsId] = useState("");
  const [password, setPassword] = useState("");

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    mode: "onBlur",
    defaultValues: {
      inputValue: "",
    },
    reValidateMode: "onBlur",
  });

  async function submit() {
    try {
      const { data } = await login({
        account: isID,
        password: password,
      });
      console.log(data);
      setIsLoggedIn({
        state: true,
        isKakao: false,
        isBasic: true,
        accessToken: data.tokenForm.accessToken,
        refreshToken: data.tokenForm.refreshToken,
        memberId: data.memberId,
        creatorId: data.creatorId,
      });
    } catch (err) {
      if (err.response.data.code === -6008) {
        alert("회원정보가 없습니다.");
      } else if (err.response.data.code === -6009) {
        alert("잘못된 비밀번호입니다.");
      } else if (err.response.data.code === -6002) {
        alert(err.response.data.message);
      } else if (err.response.data.code === -6001) {
        alert(err.response.data.message);
      }
    }
  }

  return (
    <AccountWrapper>
      <AccountTitle>로그인</AccountTitle>
      <AccountForm onSubmit={handleSubmit(submit)}>
        <InputBox>
          <InputLabel htmlFor="email">이메일주소 (아이디)</InputLabel>
          <AccountInput
            {...register("email", {
              name: "email",
              required: "아이디를 입력하세요!",
              // pattern: {
              //   value:
              //     /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$/,
              //   message: "올바른 아이디 형식을 입력해주세요.",
              // },
              onChange: (e) => {
                setIsId(e.target.value);
              },
            })}
            type="text"
            placeholder="이메일 주소를 입력해주세요."
          />

          <ErrorMessage>{errors?.email?.message}</ErrorMessage>
        </InputBox>
        <InputBox>
          <InputLabel>비밀번호</InputLabel>
          <AccountInput
            {...register("pwd", {
              name: "pwd",
              required: "비밀번호를 입력해주세요!",
              // pattern: {
              //   value:
              //     /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$/,
              //   message: "올바른 비밀번호 형식을 입력해주세요.",
              // },
              minLength: {
                value: 8,
                message: "8글자 이상 입력해주세요.",
              },
              maxLength: {
                value: 16,
                message: "16글자 이하로 입력해주세요.",
              },
              onChange: (e) => {
                setPassword(e.target.value);
              },
            })}
            type="password"
            placeholder="비밀번호를 입력해주세요."
          />
          <ErrorMessage>{errors?.pwd?.message}</ErrorMessage>
        </InputBox>
        <LoginLinkBox>
          <AnyLink to={"/" + PROCESS_ACCOUNT_URL.FINDID}>아이디찾기</AnyLink>
          <AnyLink to={"/" + PROCESS_ACCOUNT_URL.FINDPWD}>비밀번호찾기</AnyLink>
        </LoginLinkBox>
        <BtnSignUpBox>
          <AnyLink to={"/" + PROCESS_ACCOUNT_URL.SIGNUP}>회원가입</AnyLink>
          <AccountSmallBtn>로그인</AccountSmallBtn>
        </BtnSignUpBox>
      </AccountForm>
      <ImgDiv>
        <img
          style={{ cursor: "pointer" }}
          onClick={() => {
            window.location.href = KAKAO_AUTH_URL;
          }}
          src={kakaoImg}
          alt=""
        />
      </ImgDiv>
      {/* <AccountBtn
        texthovercolor={"--color-background"}
        bgcolor={"--color-primary"}
        textcolor={"--color-text"}
      >
        카카오로 시작하기
      </AccountBtn> */}
      {/* <AccountBtn
        texthovercolor={"--color-background"}
        bgcolor={"--color-account-naver"}
        textcolor={"--color-text"}
      >
        네이버로 시작하기test
      </AccountBtn>
      <AccountBtn
        texthovercolor={"--color-text"}
        bgcolor={"--color-account-apple"}
        textcolor={"--color-background"}
      >
        애플로 시작하기
      </AccountBtn> */}
    </AccountWrapper>
  );
}
