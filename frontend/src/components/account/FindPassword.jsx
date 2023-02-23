import { Link, useNavigate } from "react-router-dom";
import styled from "styled-components";
import { PROCESS_ACCOUNT_URL } from "../../static";
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
import { useState } from "react";
import { resetPwdApi } from "../../api/myPageApi";
import { CircularProgress } from "@mui/material";
import { CenterDiv } from "../../style/CommonCss";

const LoginLinkBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 34px;
`;

const AnyLink = styled(Link)`
  font-size: 0.9rem;
  font-weight: var(--weight-thin);
  text-decoration: none;
  color: var(--color-gray);

  &:hover {
    color: var(--color-black);
    font-weight: var(--weight-point);
  }
  &:active {
    transform: scale(0.9);
  }
`;

const Btn = styled(AccountSmallBtn)`
  width: 100%;
  margin: 0;
`;

const Div = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
`;

export default function FindPassword() {
  const [isID, setIsId] = useState("");
  const [isName, setIsName] = useState("");
  const [isSubmit, setIsSubmit] = useState(false);
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const submit = () => {
    setIsSubmit(true);
    resetPwdApi(isID)
      .then(({ data }) => {
        alert(
          data.email +
            "로 임시 비밀번호를 보냈어요. 로그인 후, 비밀번호를 변경해주세요."
        );
        navigate(-1);
      })
      .catch((err) => {
        if (err.response.data.code === -6008) {
          alert(err.response.data.message);
        } else {
          alert(err);
        }

        setIsSubmit(false);
      });
  };

  return (
    <AccountWrapper>
      <AccountTitle>비밀번호 찾기</AccountTitle>
      {!isSubmit ? (
        <AccountForm onSubmit={handleSubmit(submit)}>
          <InputBox>
            <InputLabel>아이디</InputLabel>
            <AccountInput
              {...register("email", {
                name: "email",
                required: "아이디를 입력하세요!",
                // 유효성 검사 파트
                // pattern: {
                //   value: /^[A-Za-z0-9._%+-]+@knu\.ac.kr$/,
                //   message: "wrong input",
                // },
                onChange: (e) => {
                  setIsId(e.target.value);
                },
              })}
              type="email"
              placeholder="아이디를 입력해주세요."
            />
            <ErrorMessage>{errors?.email?.message}</ErrorMessage>
          </InputBox>
          <InputBox>
            <InputLabel>성명</InputLabel>
            <AccountInput
              {...register("name", {
                name: "name",
                required: "이름을 입력하세요!",
                // 유효성 검사 파트
                // pattern: {
                //   value: /^[A-Za-z0-9._%+-]+@knu\.ac.kr$/,
                //   message: "wrong input",
                // },
                onChange: (e) => {
                  setIsName(e.target.value);
                },
              })}
              type="text"
              placeholder="이름을 입력해주세요."
            />
            <ErrorMessage>{errors?.name?.message}</ErrorMessage>
          </InputBox>
          <LoginLinkBox>
            <Btn>비밀번호 찾기</Btn>
          </LoginLinkBox>
        </AccountForm>
      ) : (
        <CenterDiv>
          <CircularProgress
            sx={{
              color: "var(--color-primary)",
            }}
          />
        </CenterDiv>
      )}

      <br />

      <Div>
        <AnyLink to={"/"}>메인으로</AnyLink>
        <div style={{ textAlign: "end" }}>
          <div>아직, 다 봄에 가입하지 않았다면? &nbsp;</div>
          <br />
          <AnyLink to={"/" + PROCESS_ACCOUNT_URL.SIGNUP}>회원가입</AnyLink>
        </div>
      </Div>
      <br />
    </AccountWrapper>
  );
}
