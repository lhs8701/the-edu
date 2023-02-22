import { useNavigate } from "react-router";
import { useRecoilValue, useSetRecoilState } from "recoil";
import styled from "styled-components";
import { queryClient } from "../..";
import { basicWithdraw, kakaoWithdraw } from "../../api/authApi";
import {
  getAccessTokenSelector,
  getIsKakaoSelector,
  getKakaoAuthTokenSelector,
  getRefreshTokenSelector,
  LoginState,
} from "../../atom";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";

const WithdrawBox = styled(MyPageContentBox)`
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  flex-direction: column;
`;

const Btn = styled.button`
  border: none;
  font-size: 1.2rem;
  background-color: var(--color-background);
  margin: 60px;
  &:hover {
    font-weight: var(--weight-point);
  }
`;

export default function Withdraw() {
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const refreshToken = useRecoilValue(getRefreshTokenSelector);
  const isKakaoState = useRecoilValue(getIsKakaoSelector);
  const socialToken = useRecoilValue(getKakaoAuthTokenSelector);
  const setIsLoggedIn = useSetRecoilState(LoginState);
  const navigate = useNavigate();

  const withdraw = () => {
    if (window.confirm(`The-Edu에서 탈퇴하시겠습니까?`)) {
      if (isKakaoState) {
        kakaoWithdraw(accessToken, refreshToken, socialToken)
          .then(() => {
            alert("탈퇴가 완료되었습니다.");
          })
          .catch((err) => {
            alert(err);
          });
      } else {
        basicWithdraw(accessToken, refreshToken)
          .then(() => {
            alert("탈퇴가 완료되었습니다.");
          })
          .catch((err) => {
            alert(err);
          });
      }
      setIsLoggedIn({
        state: false,
        isKakao: false,
        isBasic: false,
        memberId: -1,
        accessToken: "",
        refreshToken: "",
        creatorId: -1,
      });
      queryClient.clear();
      navigate("/");
    }
  };

  return (
    <MyPageBox>
      <MyPageTitle>회원 탈퇴</MyPageTitle>
      <WithdrawBox>
        탈퇴하시면 해당 계정의 정보가 복구되지 않습니다.
        <br />
        <br />
        <br />
        <div>
          <Btn onClick={withdraw}>네</Btn>
          <Btn
            onClick={() => {
              navigate(-1);
            }}
          >
            아니요
          </Btn>
        </div>
      </WithdrawBox>
    </MyPageBox>
  );
}
