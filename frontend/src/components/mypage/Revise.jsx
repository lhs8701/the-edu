import styled from "styled-components";
import {
  AccountBtn,
  AccountForm,
  AccountInput,
  AccountSmallBtn,
  InputBox,
  InputLabel,
} from "../../style/AccountComponentCss";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";

const TeleInput = styled(AccountInput)`
  width: 83%;
`;

const AuthBtn = styled(AccountSmallBtn)``;

const ReviseForm = styled(AccountForm)`
  width: var(--size-revise-coupon-window);
  margin: 0 auto;
`;

export default function Revise() {
  return (
    <MyPageBox>
      <MyPageTitle>개인정보 수정</MyPageTitle>
      <MyPageContentBox>
        <ReviseForm>
          <InputBox>
            <InputLabel>성명</InputLabel>
            <AccountInput type="text" placeholder="성명을 입력해주세요." />
          </InputBox>
          <InputBox>
            <InputLabel>이메일 주소</InputLabel>
            <AccountInput
              type="email"
              placeholder="이메일 주소를 입력해주세요."
            />
          </InputBox>
          <InputBox>
            <InputLabel>비밀번호</InputLabel>
            <AccountInput
              type="password"
              placeholder="비밀번호를 입력해주세요."
            />
          </InputBox>
          <InputBox>
            <InputLabel>비밀번호 확인</InputLabel>
            <AccountInput
              type="password"
              placeholder="비밀번호를 입력해주세요."
            />
          </InputBox>
          <InputBox>
            <InputLabel>휴대전화 번호</InputLabel>
            <TeleInput type="tel" placeholder="휴대전화 번호를 입력해주세요." />
            <button>인증</button>
          </InputBox>

          <AccountBtn
            texthovercolor={"--color-background"}
            bgcolor={"--color-primary"}
            textcolor={"--color-text"}
          >
            수정하기
          </AccountBtn>
        </ReviseForm>
      </MyPageContentBox>
    </MyPageBox>
  );
}
