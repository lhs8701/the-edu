import { Link } from "react-router-dom";
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

export default function FindAccount() {
  const [isName, setIsName] = useState("");
  const [isTele, setIsTele] = useState("");
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
  });

  const submit = () => {
    console.log(isName, isTele);
  };

  return (
    <AccountWrapper>
      <AccountTitle>아이디 찾기</AccountTitle>
      <AccountForm onSubmit={handleSubmit(submit)}>
        <InputBox>
          <InputLabel htmlFor="name">성명</InputLabel>
          <AccountInput
            {...register("name", {
              name: "name",
              required: "이름를 입력하세요!",

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
        <InputBox>
          <InputLabel>휴대전화 번호</InputLabel>
          <AccountInput
            {...register("tele", {
              name: "tele",
              required: "전화번호를 입력하세요!",
              pattern: {
                value: [0 - 9],
                message: "전화번호를 입력하세요.",
              },
              onChange: (e) => {
                setIsTele(e.target.value);
              },
            })}
            type="tel"
            placeholder="전화번호를 입력해주세요."
          />
          <ErrorMessage>{errors?.tele?.message}</ErrorMessage>
        </InputBox>
        <LoginLinkBox>
          <Btn>아이디 찾기</Btn>
        </LoginLinkBox>
      </AccountForm>
      <br />

      <Div>
        <AnyLink to={"/" + PROCESS_ACCOUNT_URL.FINDPWD}>비밀번호 찾기</AnyLink>
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
