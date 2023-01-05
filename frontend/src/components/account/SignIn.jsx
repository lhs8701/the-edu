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

const LoginWrapper = styled.div`
  width: 100%;
  height: 100%;
`;

const LoginTitle = styled.div`
  font-size: 1.9rem;
  font-weight: 900;
  text-align: center;
  width: 100%;
`;

const LoginForm = styled.form`
  width: 100%;
  height: 100%;
  background-color: tomato;
`;

const BtnSignUpBox = styled.div`
  width: 90%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
`;

const LoginBtn = styled.button`
  border: none;
  background-color: var(--color-primary);
  color: white;
  font-size: var(--size-login-btn);
`;

export default function SignIn() {
  return (
    <LoginWrapper>
      <LoginTitle>로그인</LoginTitle>
      <LoginForm>
        <BtnSignUpBox>
          <LoginBtn>로그인</LoginBtn>
        </BtnSignUpBox>
      </LoginForm>
    </LoginWrapper>
  );
}
