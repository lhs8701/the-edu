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
  AccountTitle,
  AccountWrapper,
  InputLabel,
} from "../../style/AccountComponent";

export default function SignUp() {
  return (
    <AccountWrapper>
      <AccountTitle>회원가입</AccountTitle>
      <AccountForm>
        <InputLabel>성명</InputLabel>
        <AccountInput placeholder="성명을 입력해주세요." />
        <InputLabel>이메일 주소</InputLabel>
        <AccountInput placeholder="이메일 주소를 입력해주세요." />
        <InputLabel>비밀번호</InputLabel>
        <AccountInput placeholder="비밀번호를 입력해주세요." />
        <AccountBtn
          texthovercolor={"--color-background"}
          bgcolor={"--color-primary"}
          textcolor={"--color-text"}
        >
          회원가입
        </AccountBtn>
      </AccountForm>
    </AccountWrapper>
  );
}
