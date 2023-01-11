import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import styled from "styled-components";
import { motion, AnimatePresence } from "framer-motion";
import {
  CATE_VALUE,
  PROCESS_ACCOUNT_URL,
  PROCESS_MAIN_URL,
  COLOR,
} from "../../static";
import {
  AccountBtn,
  AccountForm,
  AccountInput,
  AccountSmallBtn,
  AccountTitle,
  AccountWrapper,
  InputLabel,
} from "../../style/AccountComponentCss";

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

export default function SignIn() {
  return (
    <AccountWrapper>
      <AccountTitle>로그인</AccountTitle>
      <AccountForm>
        <InputLabel>이메일주소 (아이디)</InputLabel>
        <AccountInput type="email" placeholder="이메일 주소를 입력해주세요." />
        <InputLabel>비밀번호</InputLabel>
        <AccountInput type="password" placeholder="비밀번호를 입력해주세요." />
        <LoginLinkBox>
          <AnyLink to={"/" + PROCESS_ACCOUNT_URL.FINDID}>아이디찾기</AnyLink>
          <AnyLink to={"/" + PROCESS_ACCOUNT_URL.FINDPWD}>비밀번호찾기</AnyLink>
        </LoginLinkBox>
        <BtnSignUpBox>
          <AnyLink to={"/" + PROCESS_ACCOUNT_URL.SIGNUP}>회원가입</AnyLink>
          <AccountSmallBtn>로그인</AccountSmallBtn>
        </BtnSignUpBox>
      </AccountForm>
      <AccountBtn
        texthovercolor={"--color-background"}
        bgcolor={"--color-primary"}
        textcolor={"--color-text"}
      >
        카카오로 시작하기
      </AccountBtn>
      <AccountBtn
        texthovercolor={"--color-background"}
        bgcolor={"--color-account-naver"}
        textcolor={"--color-text"}
      >
        네이버로 시작하기
      </AccountBtn>
      <AccountBtn
        texthovercolor={"--color-text"}
        bgcolor={"--color-account-apple"}
        textcolor={"--color-background"}
      >
        애플로 시작하기
      </AccountBtn>
    </AccountWrapper>
  );
}
