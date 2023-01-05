import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import styled from "styled-components";
import Modal from "react-modal";
import {
  AccountBtn,
  AccountForm,
  AccountInput,
  AccountSmallBtn,
  AccountTitle,
  AccountWrapper,
  InputLabel,
} from "../../style/AccountComponent";
import Term from "./Term";

const TeleInput = styled(AccountInput)`
  width: 83%;
`;

const AuthBtn = styled(AccountSmallBtn)``;

const TermBox = styled.div`
  margin-top: 20px;
  margin-bottom: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;
`;

const TermBtn = styled.div`
  cursor: pointer;
  justify-content: center;
  display: flex;
  align-items: center;
  color: var(--color-black);
  font-size: 1.5rem;
  font-weight: var(--weight-point);
  height: 35px;
  width: 75px;
  &:active {
    font-size: 1rem;
  }
  &:hover {
    color: var(--color-primary);
  }
`;

const TermLabel = styled.label`
  &::before {
    content: "";
    background-color: ${(props) =>
      props.checked ? "#da2127" : "rgba(97, 97, 97, 0.2)"};
    width: 25px;
    height: 25px;
    border-radius: 8px;
    margin-right: 15px;
  }
  display: flex;
  align-items: center;
  font-weight: var(--weight-point);
`;

const TermCheck = styled.input`
  display: none;
  height: 100%;
`;

const ErrorMessage = styled.span`
  position: absolute;
  color: tomato;
  right: 10px;
`;

export default function SignUp() {
  const [isModalOpen, setModalOpen] = useState(false);
  return (
    <AccountWrapper>
      <AccountTitle>회원가입</AccountTitle>
      <AccountForm>
        <InputLabel>성명</InputLabel>
        <AccountInput type="text" placeholder="성명을 입력해주세요." />
        <InputLabel>이메일 주소</InputLabel>
        <AccountInput type="email" placeholder="이메일 주소를 입력해주세요." />
        <InputLabel>비밀번호</InputLabel>
        <AccountInput type="password" placeholder="비밀번호를 입력해주세요." />
        <InputLabel>비밀번호 확인</InputLabel>
        <AccountInput type="password" placeholder="비밀번호를 입력해주세요." />
        <InputLabel>생년월일</InputLabel>
        <AccountInput type="date" placeholder="입력해주세요." />
        <InputLabel>휴대전화 번호</InputLabel>
        <br />
        <TeleInput type="tel" placeholder="휴대전화 번호를 입력해주세요." />
        <AuthBtn>인증</AuthBtn>
        <TermBox>
          <TermLabel>
            <TermCheck type="checkbox" />
            [필수] 서비스 이용약관 동의
          </TermLabel>
          <ErrorMessage></ErrorMessage>
          <TermBtn
            onClick={() => {
              setModalOpen(true);
            }}
          >
            >
          </TermBtn>
        </TermBox>
        <TermBox>
          <TermLabel>
            <TermCheck type="checkbox" />
            [필수] 개인정보 수집 및 이용에 대한 동의
          </TermLabel>
          <ErrorMessage></ErrorMessage>
          <TermBtn
            onClick={() => {
              setModalOpen(true);
            }}
          >
            >
          </TermBtn>
        </TermBox>
        <br />
        <AccountBtn
          texthovercolor={"--color-background"}
          bgcolor={"--color-primary"}
          textcolor={"--color-text"}
        >
          회원가입
        </AccountBtn>
      </AccountForm>
      <Modal
        isOpen={isModalOpen}
        ariaHideApp={false}
        onRequestClose={() => setModalOpen(false)}
        style={{
          overlay: {
            position: "fixed",
            top: 0,
            left: 0,
            backgroundColor: "rgba(0, 0, 0, 0.2)",
            width: "100%",
            height: "100%",
          },
          content: {
            overflow: "hidden",
            width: "25%",
            height: "75%",
            top: "10%",
            left: "37.5%",
          },
        }}
      >
        {<Term setModalOpen={setModalOpen} />}
      </Modal>
    </AccountWrapper>
  );
}
