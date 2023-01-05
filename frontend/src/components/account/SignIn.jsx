import { useState } from "react";
import { Link } from "react-router-dom";
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
  font-size: 2rem;
  font-weight: 900;
  text-align: center;
  width: 100%;
`;

const LoginForm = styled.form``;

export default function SignIn() {
  return (
    <LoginWrapper>
      <LoginTitle>로그인</LoginTitle>
    </LoginWrapper>
  );
}
