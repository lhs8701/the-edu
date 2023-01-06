import { Outlet } from "react-router";
import styled from "styled-components";
import MyPageSideBar from "../components/MyPageSideBar";

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
      <MyPageSideBar />
      <Outlet />
    </MyPageWrapper>
  );
}
