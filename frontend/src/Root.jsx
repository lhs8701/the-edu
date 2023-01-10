import { useRef } from "react";
import { Outlet } from "react-router";
import styled from "styled-components";
import Footer from "./components/Footer";
import Header from "./components/Header";

const Wrapper = styled.main`
  width: 1100px;
  min-height: 100vh;
  margin: 0 auto;
`;

const UpBtn = styled.button`
  position: fixed;
  bottom: 3vh;
  height: 50px;
  width: 50px;
  right: 3vw;
  background-color: #4d4d4c;
  border: none;
  border-radius: 50%;
  &:hover {
    background-color: #868686;
  }
  &:active {
    scale: 0.9;
  }
  color: var(--color-background);
`;

export default function Root() {
  const upRef = useRef(null);

  const goUp = () => {
    upRef.current.scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  };
  return (
    <span ref={upRef}>
      <Wrapper>
        <Header />
        <Outlet />
      </Wrapper>
      <Footer />
      <UpBtn onClick={goUp}>Up</UpBtn>
    </span>
  );
}
