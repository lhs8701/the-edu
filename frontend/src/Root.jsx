import { Outlet } from "react-router";
import styled from "styled-components";
import Header from "./components/Header";

const Wrapper = styled.div`
  width: 100%;
  background-color: tomato;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
`;

export default function Root() {
  return (
    <>
      <Header />
      <Wrapper>
        <Outlet />
      </Wrapper>
    </>
  );
}
