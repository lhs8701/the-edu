import { Outlet } from "react-router";
import styled from "styled-components";
import Header from "./components/Header";

const Wrapper = styled.div`
  width: 1100px;
  min-height: 100vh;
  margin: 0 auto;
  justify-content: center;
`;

export default function Root() {
  return (
    <>
      <Wrapper>
        <Header />
        <Outlet />
      </Wrapper>
    </>
  );
}
