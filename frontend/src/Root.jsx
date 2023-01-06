import { Outlet } from "react-router";
import styled from "styled-components";
import Footer from "./components/Footer";
import Header from "./components/Header";

const Wrapper = styled.main`
  width: 1100px;
  min-height: 100vh;
  margin: 0 auto;
`;

export default function Root() {
  return (
    <>
      <Wrapper>
        <Header />
        <Outlet />
      </Wrapper>
      <Footer />
    </>
  );
}
