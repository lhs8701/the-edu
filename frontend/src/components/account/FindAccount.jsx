import { Link } from "react-router-dom";
import styled from "styled-components";
import { PROCESS_ACCOUNT_URL } from "../../static";
import {
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
  return (
    <AccountWrapper>
      <AccountTitle>아이디 찾기</AccountTitle>
      <AccountForm>
        <InputLabel>성명</InputLabel>
        <AccountInput type="tel" placeholder="비밀번호를 입력해주세요." />
        <InputLabel>휴대전화 번호</InputLabel>
        <AccountInput type="email" placeholder="이메일 주소를 입력해주세요." />
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
