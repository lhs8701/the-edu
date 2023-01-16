import { AnimatePresence, motion } from "framer-motion";
import {
  useCallback,
  useEffect,
  useLayoutEffect,
  useRef,
  useState,
} from "react";
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
  display: flex;
  justify-content: center;
  background-color: rgb(255, 255, 255);
  width: 100%;
  height: 100%;
  /* @media screen and (max-width: 1280px) {
    grid-template-columns: 100%;
  } */
  position: relative;
`;

const VideoTab = styled.div`
  height: 100%;
  width: 79%;
  position: relative;
  @media screen and (max-width: 1280px) {
    width: 100%;
  }
`;
const BarTab = styled.div`
  height: 100%;
  width: 21%;
  @media screen and (max-width: 1280px) {
    display: none;
  }
  z-index: 2;
`;

const AniBarTab = styled(BarTab)`
  width: 100vw;
  position: absolute;
  top: 0;
  z-index: 4;
  transform: ${(props) =>
    props.ison ? " translateX(-110%)" : " translateX(0%)"};
  transition: transform 0.4s ease-in-out;
  display: block;
  /* @media screen and (max-width: 1280px) {
    display: ${(props) => (props.ison ? "none" : "block")};
  } */
`;

const ArcodianBtn = styled.button`
  position: absolute;
  width: 2rem;
  height: 1.5rem;
  top: -20px;
  left: 0;
  background-color: var(--color-text);
  @media screen and (min-width: 1280px) {
    display: none;
  }
  z-index: 5;
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
  const [isCollapse, setIsCollapse] = useState(true);
  const [currentWidth, setCurrentWidth] = useState(0);
  const sideBarRef = useRef(null);
  const wrapperRef = useRef(null);
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
    if (Number(keyCode) === 44) {
      stopPrntScr();
      setCapture(true);
      wrapperRef.current.focus();
      setTimeout(() => {
        setCapture(false);
      }, 10000);
    }
  };

  const handleButtonClick = useCallback(() => {
    setIsCollapse(!isCollapse);
  }, [isCollapse]);

  return (
    <Hm>
      <AnimatePresence>
        <Wrapper tabIndex={0} onKeyUp={keyUpHandler} ref={wrapperRef}>
          {!isCapture ? null : (
            <BlockCapBox>
              <H1>캡쳐 금지</H1>
            </BlockCapBox>
          )}
          <VideoTab>
            <Title>unitInfo?.title</Title>
            <Player />
            <ArcodianBtn
              onClick={() => {
                handleButtonClick();
              }}
            />
          </VideoTab>

          <AniBarTab
            // initial={{ display: !isCollapse ? "none" : "block" }}
            // animate={{
            //   translateX: isCollapse ? "-150%" : 0,
            // }}
            // transition={{
            //   type: "linear",
            //   duration: 0.2,
            // }}
            ison={isCollapse}
          >
            <PlayerSidebar />
          </AniBarTab>

          <BarTab>
            <PlayerSidebar />
          </BarTab>
        </Wrapper>
      </AnimatePresence>
    </Hm>
  );
}
