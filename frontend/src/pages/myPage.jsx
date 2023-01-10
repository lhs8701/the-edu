import { Outlet } from "react-router";
import styled from "styled-components";
import SideBar from "../components/SideBar";
import { BAR_LIST } from "../static";

const MyPageWrapper = styled.div`
  width: 100%;
  min-height: 90vh;
  display: flex;
  position: relative;
  justify-content: space-between;
`;

export default function MyPage() {
  return (
    <MyPageWrapper>
      <SideBar barList={BAR_LIST} />
      <Outlet />
    </MyPageWrapper>
  );
}
