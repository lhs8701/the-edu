import { AnimatePresence, motion } from "framer-motion";
import { useCallback, useEffect, useRef, useState } from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getCurriculumStatusApi } from "../api/courseApi";
import {
  getLatestRecordApi,
  postMyRecordApi,
  postWatchAllApi,
} from "../api/playerApi";
import { getUnitVideoApi } from "../api/unitApi";
import { getAccessTokenSelector, getLoginState } from "../atom";
import Player from "../components/Player/Player";
import PlayerSidebar from "../components/Player/PlayerSidebar";

const Hm = styled.div`
  width: 95vw;
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
  width: 75%;
  position: relative;
  @media screen and (max-width: 1180px) {
    width: 100%;
  }
`;
const BarTab = styled.div`
  height: 100%;
  width: 25%;
  @media screen and (max-width: 1180px) {
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
  @media screen and (min-width: 1180px) {
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
  font-size: 1.6rem;
`;

export default function PlayerRoot() {
  const [isCapture, setCapture] = useState(false);
  const [isCollapse, setIsCollapse] = useState(true);
  const [menu, setMenu] = useState(0);
  const { unitId, courseId } = useParams();
  const loginState = useRecoilValue(getLoginState);
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const wrapperRef = useRef(null);
  const [loading, setLoading] = useState(false);
  const [unitInfo, setUnitInfo] = useState();
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
    playedSec: 0, //전체 시간 초
    done: false,
  });

  useQuery(
    ["userCurriStatus", courseId],
    () => {
      return getCurriculumStatusApi(accessToken, courseId);
    },
    {
      enabled: !!courseId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  window.onbeforeunload = function () {
    exitUnit();
  };

  const exitUnit = () => {
    postMyRecordApi(accessToken, unitId, Number(videoVal.playedSec));
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

  const getUnitInfo = () => {
    getUnitVideoApi(unitId, accessToken)
      .then(({ data }) => {
        if (data?.code) {
          alert("err");
        }
        setUnitInfo(data);
      })
      .catch((err) => {
        alert(err);
      });
  };

  const CaptureBlocker = () => {
    return (
      <BlockCapBox>
        <H1>캡쳐 금지</H1>
      </BlockCapBox>
    );
  };

  const getLatestRecord = () => {
    getLatestRecordApi(accessToken, unitId)
      .then(({ data }) => {
        setVideoVal({
          ...videoVal,
          playedSec: data,
        });
        setLoading(true);
      })
      .catch((err) => {
        console.log("새 강의");
        setLoading(true);
      });
  };

  useEffect(() => {
    getUnitInfo();
    getLatestRecord();
  }, []);

  useEffect(() => {
    if (videoVal.played >= 0.8 && !videoVal.done) {
      setVideoVal({ ...videoVal, done: true });
      postWatchAllApi(accessToken, unitId);
    }
  }, [videoVal.played]);

  return (
    <Hm>
      <AnimatePresence>
        <Wrapper tabIndex={0} onKeyUp={keyUpHandler} ref={wrapperRef}>
          {isCapture && <CaptureBlocker />}
          {loading && (
            <VideoTab>
              <Title>{unitInfo?.title}</Title>
              {unitInfo && (
                <Player
                  setMenu={setMenu}
                  videoVal={videoVal}
                  setVideoVal={setVideoVal}
                  unitInfo={unitInfo}
                />
              )}

              <ArcodianBtn
                onClick={() => {
                  handleButtonClick();
                }}
              />
            </VideoTab>
          )}
          <AniBarTab ison={isCollapse}>
            <PlayerSidebar uniInfo={unitInfo} />
          </AniBarTab>
          <BarTab>
            {unitInfo && (
              <PlayerSidebar
                menu={menu}
                setMenu={setMenu}
                exitUnit={exitUnit}
                unitInfo={unitInfo}
                unitId={unitId}
                courseId={courseId}
              />
            )}
          </BarTab>
        </Wrapper>
      </AnimatePresence>
    </Hm>
  );
}
