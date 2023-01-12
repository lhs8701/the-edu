import { Outlet } from "react-router";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { BAR_LIST } from "../static";
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
  height: 300px;
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
  const SideBar = ({ barList }) => {
    return (
      <SideBarBox>
        <NavBox>
          {barList.list.map((target, idx) => {
            return (
              <NavTab>
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
