import { useLayoutEffect } from "react";
import { Outlet, useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getLoginState } from "../atom";
import { BAR_LIST, PROCESS_ACCOUNT_URL } from "../static";
import { Wrapper } from "../style/CommonCss";
import { MyLink, NavBox, NavTab } from "../style/SideBarCss";

const MyPageWrapper = styled(Wrapper)`
  width: 100%;
  min-height: 90vh;
  display: flex;
  position: relative;
  justify-content: space-between;
`;

const SideBarBox = styled.nav`
  width: 17%;
  height: 350px;
  border-radius: 10px;
  top: 200px;
  position: sticky;
  position: -webkit-sticky;
  background: var(--color-sidebar);
  box-shadow: 0 0px 10px rgb(0 0 0 / 16%), 0 0px 0px rgb(0 0 0 / 16%);
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  padding: 15px 0;
  box-sizing: border-box;
`;

export default function MyPage() {
  const loginState = useRecoilValue(getLoginState);

  useLayoutEffect(() => {
    if (!loginState) {
      window.location.replace(PROCESS_ACCOUNT_URL.LOGIN);
      alert("확인되지 않은 접근입니다.");
    }
  }, []);

  const SideBar = ({ barList }) => {
    return (
      <SideBarBox>
        <NavBox>
          {barList.list.map((target, idx) => {
            return (
              <NavTab key={idx}>
                <MyLink preventScrollReset={true} to={target.url}>
                  {target.name}
                </MyLink>
              </NavTab>
            );
          })}
        </NavBox>
      </SideBarBox>
    );
  };

  return (
    <MyPageWrapper>
      <SideBar barList={BAR_LIST} />
      <Outlet />
    </MyPageWrapper>
  );
}
