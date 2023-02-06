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
  top: 45%;
  left: 45%;
  z-index: 10;
  @media screen and (max-width: 600px) {
    left: 40%;
  }
`;
const VideoIcon = styled(FontAwesomeIcon)`
  width: 6rem;
  height: 6rem;
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

export default function Player({ unitInfo, videoVal, setVideoVal }) {
  const videoRef = useRef(null); //props로 컨트롤러로 슉 넘겨
  const fullRef = useRef(null);
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

  useEffect(() => {
    setTimeout(() => {
      goToRecord().then((video) => {
        console.log(videoVal.playedSec);
        video.seekTo(videoVal.playedSec);
      });
    }, 100);
  }, [url]);

  async function goToRecord() {
    return await videoRef.current;
  }

  return (
    <CControl
      mouse={controlOn}
      onMouseEnter={cOnHandler}
      onMouseMove={cMoveHandeler}
      onMouseLeave={cOffHandler}
    >
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
              <VideoIcon icon={faCirclePlay} />
            ) : (
              <VideoIcon icon={faCirclePause} />
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
          animate={{ opacity: controlOn ? 1 : 0 }}
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
    </CControl>
  );
}
