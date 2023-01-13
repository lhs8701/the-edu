import { useRef, useState } from "react";
import styled from "styled-components";
import Player from "../components/Player/Player";
import PlayerSidebar from "../components/Player/PlayerSidebar";

const Hm = styled.div`
  width: 90vw;
  height: 100vh;
  margin: 0 auto;
  padding: 2rem 0;
  box-sizing: border-box;
`;

const BlockCapBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: black;
  height: 100%;
  width: 100%;
  z-index: 1;
  position: absolute;
`;

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: 79% 22%;
  justify-content: center;
  background-color: rgb(255, 255, 255);
  width: 100%;
  height: 100%;
  @media screen and (max-width: 1024px) {
    grid-template-columns: 100%;
  }
  position: relative;
`;

const VideoTab = styled.div`
  height: 100%;
  width: 100%;
  position: relative;
`;
const BarTab = styled.div`
  height: 100%;
  @media screen and (max-width: 1024px) {
    display: none;
  }
`;

const H1 = styled.h1`
  font-weight: bold;
  font-size: 3rem;
  color: white;
`;

const Title = styled.div`
  width: 100%;
  height: 5%;
  font-weight: var(--weight-point);
  color: black;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
`;

export default function PlayerRoot() {
  const [isCapture, setCapture] = useState(false);
  window.onbeforeunload = function () {
    // exitPlayer();
  };
  function stopPrntScr() {
    var inpFld = document.createElement("input");
    inpFld.setAttribute("value", ".");
    inpFld.setAttribute("width", "0");
    inpFld.style.height = "0px";
    inpFld.style.width = "0px";
    inpFld.style.border = "0px";
    document.body.appendChild(inpFld);
    inpFld.select();
    document.execCommand("copy");
    inpFld.remove(inpFld);
  }

  const keyUpHandler = (e) => {
    var keyCode = e.keyCode ? e.keyCode : e.which;
    if (keyCode == 44) {
      stopPrntScr();
      setCapture(true);
      wrapperRef.current.focus();
      setTimeout(() => {
        setCapture(false);
      }, 10000);
    }
  };

  const wrapperRef = useRef();
  return (
    <Hm>
      <Wrapper tabIndex={0} onKeyUp={keyUpHandler} ref={wrapperRef}>
        {!isCapture ? null : (
          <BlockCapBox>
            <H1>캡쳐 금지</H1>
          </BlockCapBox>
        )}
        <VideoTab>
          <Title>unitInfo?.title</Title>
          <Player />
        </VideoTab>
        <BarTab>
          <PlayerSidebar />
        </BarTab>
      </Wrapper>
    </Hm>
  );
}
