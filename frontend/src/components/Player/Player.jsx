import { AnimatePresence, motion } from "framer-motion";
import styled from "styled-components";
import ReactPlayer from "react-player";
import { useEffect, useRef, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCirclePlay } from "@fortawesome/free-solid-svg-icons";
import { faCirclePause } from "@fortawesome/free-solid-svg-icons";
import screenfull from "screenfull";
import Controller from "./Controller";
import { STATIC_URL } from "../../static";

const Splayer = styled(ReactPlayer)`
  background-color: black;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Clicker = styled.span`
  width: 100%;
  height: 100%;
  background-color: transparent;
`;
const CControl = styled(motion.span)`
  width: 100%;
  height: 95%;
  background-color: transparent;
  position: relative;
  cursor: ${(props) => (props.mouse ? "default" : "none")}; //props 활용
  display: flex;
  align-items: flex-end;
  justify-content: flex-start;
`;
const PlayAni = styled(motion.div)`
  position: absolute;
  top: 48%;
  left: 45%;
  z-index: 10;
  @media screen and (max-width: 600px) {
    left: 40%;
  }
`;
const Icon = styled(FontAwesomeIcon)`
  width: 5rem;
  height: 5rem;
  color: rgba(0, 0, 0, 0.5);
  /* @media screen and (min-width: 1280px) {
    display: none;
  } */
`;
const ControlTab = styled(motion.div)`
  z-index: 1;
`;

const LoadingPlayer = styled(motion.div)`
  height: 100%;
`;

const VideoRelativeDiv = styled.div`
  position: relative;
  height: 97%;
  width: 98%;
`;

export default function Player({ unitInfo }) {
  const videoRef = useRef(null); //props로 컨트롤러로 슉 넘겨
  const fullRef = useRef(null);
  const [videoVal, setVideoVal] = useState({
    playing: false, // 재생중인지
    muted: false, // 음소거인지
    controls: false, // 기본으로 제공되는 컨트롤러 사용할건지
    pip: false, //pipmode
    volume: 0.5, // 볼륨크기
    playbackRate: 1.0, // 배속
    played: 0, // 재생의 정도 (value)
    seeking: false, // 재생바를 움직이고 있는지
    duration: 0, // 전체 시간
    full: false, // 전체모드
    cc: false,
    playedSec: 0,
  });
  const [controlOn, setControl] = useState(false);
  const [isBar, setIsBar] = useState(false);
  const [isSeek, setIsSeek] = useState(false);
  const [isFull, setIsFull] = useState(false);
  const [isBarTabs, setIsBarTabs] = useState({
    rateBar: false,
    ccBar: false,
    gearBar: false,
  });
  let mouseX = 0;
  const url = STATIC_URL + unitInfo?.videoInfo?.filePath;
  const cMoveHandeler = (e) => {
    setControl(true);
    if (!isBar) {
      let timeout;
      (() => {
        clearTimeout(timeout);
        timeout = setTimeout(() => {
          if (mouseX === e.clientX && !isBar) {
            setControl(false);
          } else {
            mouseX = e.clientX;
          }
        }, 4000);
      })();
    }
  };
  const cOnHandler = () => {
    setControl(true);
    if (!isBar) {
      let timeout;
      (() => {
        clearTimeout(timeout);
        timeout = setTimeout(() => setControl(false), 3000);
      })();
    }
  };

  const cOffHandler = () => {
    setControl(false);
    setIsBarTabs({ rateBar: false, ccBar: false, gearBar: false });
  };

  const progressHandler = (changeState) => {
    if (!isSeek) {
      setVideoVal({
        ...videoVal,
        played: changeState.played,
        playedSec: changeState.playedSeconds.toFixed(),
      });
    }
  };

  useEffect(() => {
    if (controlOn) {
      screenfull.toggle(fullRef.current);
    }
  }, [isFull]);

  return (
    <CControl
      mouse={controlOn}
      onMouseEnter={cOnHandler}
      onMouseMove={cMoveHandeler}
      onMouseLeave={cOffHandler}
    >
      {url === "s" ? (
        <LoadingPlayer
          initial={{ backgroundColor: "#d4d4d4" }}
          animate={{ backgroundColor: "#aaa9a9" }}
          transition={{
            ease: "easeInOut",
            repeat: Infinity,
            repeatType: "reverse",
            repeatDelay: 0.5,
          }}
        ></LoadingPlayer>
      ) : (
        <VideoRelativeDiv ref={fullRef}>
          <AnimatePresence>
            <PlayAni
              initial={{ opacity: 0 }}
              animate={{
                opacity: videoVal.playing && controlOn ? 1 : 0,
              }}
              exit={{ opacity: 0 }}
            >
              {!videoVal.playing ? (
                <Icon icon={faCirclePlay} />
              ) : (
                <Icon icon={faCirclePause} />
              )}
            </PlayAni>
          </AnimatePresence>
          <Clicker
            onClick={() => {
              setVideoVal({ ...videoVal, playing: !videoVal.playing });
            }}
          >
            <Splayer
              ref={videoRef}
              url={url}
              // url={"http://media2.panda79.com/online211214/19.mp4"}
              playing={videoVal.playing}
              muted={videoVal.muted}
              controls={false} // 플레이어 컨트롤 노출 여부
              pip={videoVal.pip} // pip 모드 설정 여부
              loop={false} // 반복안함
              volume={videoVal.volume} // 소리조절 기능
              playbackRate={videoVal.playbackRate} // 배속기능
              onProgress={progressHandler} // 재생 및 로드된 시점을 반환
              light={false}
              width="100%"
              height="100%"
            />
          </Clicker>
          <ControlTab
            onMouseEnter={() => {
              setIsBar(true);
            }}
            onMouseLeave={() => {
              setIsBar(false);
            }}
            // animate={{ opacity: controlOn ? 1 : 0 }}
          >
            <Controller
              videoVal={videoVal}
              setVideoVal={setVideoVal}
              setIsSeek={setIsSeek}
              video={videoRef.current}
              isBarTabs={isBarTabs}
              setIsBarTabs={setIsBarTabs}
              setIsFull={setIsFull}
            />
          </ControlTab>
        </VideoRelativeDiv>
      )}
    </CControl>
  );
}
