import { useState } from "react";
import styled from "styled-components";
import {
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
import { login } from "../../api/authApi";
import { useNavigate } from "react-router";
import { PROCESS_ADMIN_URL } from "../../static";

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

export default function SignIn({ setLoginState }) {
  const [isID, setIsId] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
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
      setLoginState({
        state: true,
        accessToken: data.accessToken,
        refreshToken: data.refreshToken,
      });
      navigate(PROCESS_ADMIN_URL.DASHBOARD);
    } catch (err) {
      console.log(err.response.status);
      alert("로그인 오류입니다.");
    }
  }

  return (
    <AccountWrapper>
      <br />
      <br />
      <AccountTitle>관리자 로그인</AccountTitle>
      <AccountForm onSubmit={handleSubmit(submit)}>
        <InputBox>
          <InputLabel htmlFor="email">이메일주소 (아이디)</InputLabel>
          <AccountInput
            {...register("email", {
              name: "email",
              required: "아이디를 입력하세요!",
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
        <BtnSignUpBox>
          <AccountSmallBtn>로그인</AccountSmallBtn>
        </BtnSignUpBox>
      </AccountForm>
    </AccountWrapper>
  );
}
