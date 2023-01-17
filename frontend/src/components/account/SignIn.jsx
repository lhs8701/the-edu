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
  ErrorMessage,
  InputBox,
  InputLabel,
} from "../../style/AccountComponentCss";
import { useForm } from "react-hook-form";

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
  const [isID, setIsId] = useState("");
  const [password, setPassword] = useState("");
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm({
    mode: "onBlur",
    defaultValues: {
      inputValue: "",
    },
    reValidateMode: "onBlur",
  });

  const submit = () => {
    try {
      console.log(isID, password);
      alert("Ss");
    } catch {}
  };
  console.log("eee");
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
        네이버로 시작하기test
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
