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
  right: 2.5vw;
  background-color: var(--color-background);
  color: var(--color-text);
  border: none;
  border-radius: 50%;
  box-shadow: 0 2px 20px rgb(0 0 0 / 16%), 0 4px 4px rgb(0 0 0 / 16%);
  &:hover {
    background-color: var(--color-primary);
    color: var(--color-background);
  }
  &:active {
    scale: 0.9;
  }
`;

export default function Root() {
  const upRef = useRef(null);

  const goUp = () => {
    upRef.current.scrollIntoView({
      behavior: "auto",
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
