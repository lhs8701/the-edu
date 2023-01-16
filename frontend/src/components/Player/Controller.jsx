import { motion } from "framer-motion";
import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faVolumeXmark,
  faPlay,
  faVolumeUp,
  faGear,
  faExpand,
  faPause,
  faClosedCaptioning,
} from "@fortawesome/free-solid-svg-icons";

import { faClosedCaptioning as regular } from "@fortawesome/free-regular-svg-icons";
import pip from "../../images/pip.png";
import forward from "../../images/plus.png";
import rewind from "../../images/minus.png";
import Slider from "@mui/material/Slider";

import { useEffect, useState } from "react";

const BarWarpper = styled.div`
  height: 60px;
  width: 100%;
  background-color: white;
  padding-bottom: 20px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: absolute;
  background-color: transparent;
  z-index: 3;
  /* background-image: linear-gradient(
    rgba(0, 0, 0, 0),
    rgba(0, 0, 0, 0.1),
    rgba(0, 0, 0, 0.2),
    rgba(0, 0, 0, 0.3),
    rgba(0, 0, 0, 0.4),
    rgba(0, 0, 0, 0.5),
    rgba(0, 0, 0, 0.6),
    rgba(0, 0, 0, 0.7),
    rgba(0, 0, 0, 0.8),
    rgba(0, 0, 0, 0.9),
    rgba(0, 0, 0, 1) 70%
  ); */
  bottom: 0;
  color: white;
`;

const ProgressTab = styled.div`
  padding: 0 15px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  @media screen and (max-width: 768px) {
    padding: 0 8px;
  }
`;
const ControlTab = styled.div`
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  padding: 0 15px;
  box-sizing: border-box;
  width: 100%;
  @media screen and (max-width: 768px) {
    padding: 0 8px;
  }
`;

const Icon = styled(FontAwesomeIcon)`
  cursor: pointer;
  width: 25px;
  height: 25px;
  @media screen and (max-width: 1280px) {
    width: 18px;
    height: 18px;
  }
  @media screen and (max-width: 768px) {
    width: 13px;
    height: 13px;
  }
  @media screen and (max-width: 360px) {
    width: 10px;
    height: 10px;
  }
`;

const Img = styled.img`
  cursor: pointer;
  width: 25px;
  height: 25px;
  @media screen and (max-width: 1280px) {
    width: 18px;
    height: 18px;
  }
  @media screen and (max-width: 768px) {
    width: 13px;
    height: 13px;
  }
  @media screen and (max-width: 360px) {
    width: 10px;
    height: 10px;
  }
  padding-top: 3px;
`;

const IconTab = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  flex-shrink: 1;
`;


const IconSecTab = styled(IconTab)``;

const VolumnTab = styled(motion.div)`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const VolumeBar = styled(motion.div)`
  margin-left: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 4vw;
`;

const RateTab = styled(motion.div)`
  position: relative;
  cursor: pointer;
`;

const GearTab = styled(RateTab)``;

const RateBar = styled(motion.div)`
  position: absolute;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  bottom: 25px;
  background-color: rgba(0, 0, 0, 0.7);
  text-align: center;
`;

const GearBar = styled(RateBar)`
  background-color: rgba(0, 0, 0, 0.7);
  padding-top: 3px;
`;

const Rate = styled(motion.div)`
  border-bottom: 1px solid white;
  padding: 5px 15px;
  padding: 5px;
  width: 85%;
  font-size: 0.8rem;
`;

const Cc = styled(motion.div)`
  white-space: nowrap;
  padding: 5px 10px;
  border-bottom: 1px solid white;
`;

const TimeTab = styled.div`
  margin-left: 15px;
  font-size: 1rem;
`;

const IconDiv = styled(motion.div)`
  height: 100%;
  margin: 0 10px;
  font-size: 0.8rem;
`;

  

export default function Controller({
  vRef,
  video,
  videoVal,
  setVideoVal,
  setIsSeek,
  setIsFull,
  setIsBarTabs,
  isBarTabs,
}) {
  const videoRef = video;
  const [videoTimeVal, setVideoTimeVal] = useState(0);
  const [barOn, setBar] = useState(false);
  const currentTime =
    videoRef && videoRef ? videoRef?.getCurrentTime() : "00:00";

  const duration = videoRef && videoRef ? videoRef?.getDuration() : "00:00";

  // 남은시간

  const elapsedTime = format(currentTime);

  // 영상 총 시간을 00:00 형식으로 바꾼다. (영상 하단 00:00/00:00 에 들어갈 부분)
  const totalDuration = format(duration);

  const playHandler = () => {
    setVideoVal({ ...videoVal, playing: !videoVal.playing });
  };

  const fullHandler = () => {
    setIsFull((prev) => !prev);
  };

  const muteHandler = () => {
    setVideoVal({ ...videoVal, muted: !videoVal.muted });
  };

  const volumeChangeHandler = (e, newValue) => {
    setVideoVal({
      ...videoVal,
      volume: parseFloat(newValue / 100),
      muted: newValue === 0 ? true : false,
    });
  };

  const volumeSeekUpHandler = (e, newValue) => {
    setVideoVal({
      ...videoVal,
      volume: parseFloat(newValue / 100),
      muted: newValue === 0 ? true : false,
    });
  };

  const rewindHandler = () => {
    videoRef?.seekTo(videoRef?.getCurrentTime() - 5);
  };

  const forwardHandler = () => {
    videoRef?.seekTo(videoRef?.getCurrentTime() + 5);
  };

  const pipHandler = () => {
    setVideoVal({ ...videoVal, pip: !videoVal.pip });
  };

  const playBackChangeHandler = (rate) => {
    setVideoVal({
      ...videoVal,
      playbackRate: rate,
    });
  };
  const ccFalse = () => {
    setVideoVal({
      ...videoVal,
      cc: false,
    });
  };
  const ccTrue = () => {
    setVideoVal({
      ...videoVal,
      cc: true,
    });
  };

  const BarOff = () => {
    setBar(false);
  };

  const BarOn = () => {
    setBar(true);
  };

  // 재생 컨트롤러가 onChange()시 발생하는 함수
  const onSeekChangeHandler = (e, newValue) => {
    setVideoVal({ ...videoVal, played: parseFloat(newValue / 100) });
  };

  // 재생 컨트롤러를 움직이고 있을 때 발생하는 함수
  const seekMouseDownHandler = (e) => {
    setIsSeek(true);
  };

  // 재생 컨트롤러에서 조정을 완료했을 때 (slider onChangeCommitted시 발생하는 함수)
  const seekMouseUpHandler = (e, newValue) => {
    setIsSeek(false);
    videoRef?.seekTo(newValue / 100, "fraction");
  };

  const onChangeBitrate = (level) => {
    const internalPlayer = videoRef?.getInternalPlayer("hls");

    if (internalPlayer) {
      // currentLevel expect to receive an index of the levels array
      internalPlayer.currentLevel = level;
    }
  };

  function format(seconds) {
    if (isNaN(seconds)) {
      return `00:00`;
    }
    const date = new Date(seconds * 1000);
    const hh = date.getUTCHours();
    const mm = date.getUTCMinutes();
    const ss = pad(date.getUTCSeconds());
    if (hh) {
      return `${hh}:${pad(mm)}:${ss}`;
    }
    return `${mm}:${ss}`;
  }

  function pad(string) {
    return ("0" + string).slice(-2);
  }

  const valueLabelFormat = (value) => {
    // let _elapsedTime = value;
    // console.log((duration / 60) * value);
    // _elapsedTime = elapsedTime;
    return format((duration / 100) * value);
  };

  const TabVari = {
    hover: {
      scale: "1.1",
    },
    tap: {
      scale: "1",
    },
  };

  if (Math.trunc(currentTime / 60) !== videoTimeVal && videoRef) {
    setVideoTimeVal(Math.trunc(currentTime / 60));
  }

  useEffect(() => {
    setVideoVal({ ...videoVal, duration: Math.trunc(duration) });
  }, [duration]);

  return (
    <BarWarpper>
      <ProgressTab>
        <Slider
          valueLabelDisplay="auto"
          track="false"
          min={0}
          max={100}
          value={videoVal.played * 100}
          onChange={onSeekChangeHandler}
          onMouseDown={seekMouseDownHandler}
          onChangeCommitted={seekMouseUpHandler}
          valueLabelFormat={valueLabelFormat}
          sx={{
            color: "#567FE8",
            height: 4,
            "& .MuiSlider-thumb": {
              width: 8,
              height: 8,

              "&:before": {
                boxShadow: "0 2px 12px 0 rgba(0,0,0,0.4)",
              },
              "&:hover, &.Mui-focusVisible": {
                boxShadow: `0px 0px 0px 8px rgb(0 0 0 / 16%)`,
              },
              "&.Mui-active": {
                width: 20,
                height: 20,
              },
            },
            "& .MuiSlider-rail": {
              opacity: 0.28,
            },

            "& .MuiSlider-valueLabel": {
              lineHeight: 1.2,
              fontSize: 12,
              background: "unset",
              padding: 0,
              width: 32,
              height: 32,
              borderRadius: "50% 50% 50% 0",
              backgroundColor: "#567FE8",
              transformOrigin: "bottom left",
              transform: "translate(50%, -100%) rotate(-45deg) scale(0)",
              "&:before": { display: "none" },
              "&.MuiSlider-valueLabelOpen": {
                transform: "translate(50%, -100%) rotate(-45deg) scale(1)",
              },
              "& > *": {
                transform: "rotate(45deg)",
              },
              "&.MuiSlider-dragging": {
                "& .MuiSlider-thumb, & .MuiSlider-track": {
                  transition: "none",
                },
              },
            },
          }}
        />
        <TimeTab>
          {elapsedTime}/{totalDuration}
        </TimeTab>
      </ProgressTab>
      <ControlTab>
        <IconTab>
          <IconDiv variants={TabVari} whileHover="hover" whileTap="tap">
            <Img src={rewind} alt="no" onClick={rewindHandler} />
          </IconDiv>
          <IconDiv variants={TabVari} whileHover="hover" whileTap="tap">
            {!videoVal.playing ? (
              <Icon icon={faPlay} onClick={playHandler}></Icon>
            ) : (
              <Icon icon={faPause} onClick={playHandler}></Icon>
            )}
          </IconDiv>
          <IconDiv variants={TabVari} whileHover="hover" whileTap="tap">
            <Img src={forward} alt="no" onClick={forwardHandler} />
          </IconDiv>
          <VolumnTab onMouseEnter={BarOn} onMouseLeave={BarOff}>
            <IconDiv>
              {videoVal.muted ? (
                <Icon icon={faVolumeXmark} onClick={muteHandler} />
              ) : (
                <Icon icon={faVolumeUp} onClick={muteHandler} />
              )}
            </IconDiv>
            <VolumeBar animate={{ scale: barOn ? 1 : 0 }}>
              <Slider
                min={0}
                max={100}
                value={
                  videoVal.muted
                    ? 0
                    : !videoVal.muted && videoVal.volume === 0
                    ? 50
                    : videoVal.volume * 100
                }
                onChange={volumeChangeHandler}
                aria-label="Default"
                onChangeCommitted={volumeSeekUpHandler}
                valueLabelDisplay="off"
                sx={{
                  color: "#567FE8",
                  height: 4,
                  "& .MuiSlider-thumb": {
                    width: 8,
                    height: 8,
                    "&:before": {
                      boxShadow: "0 2px 12px 0 rgba(0,0,0,0.4)",
                    },
                    "&:hover, &.Mui-focusVisible": {
                      boxShadow: `0px 0px 0px 8px rgb(0 0 0 / 16%)`,
                    },
                    "&.Mui-active": {
                      width: 20,
                      height: 20,
                    },
                  },
                  "& .MuiSlider-rail": {
                    opacity: 0.28,
                  },
                }}
              />
            </VolumeBar>
          </VolumnTab>
        </IconTab>

        <IconSecTab>
          <RateTab
            onClick={() => {
              setIsBarTabs((prev) => ({ ...prev, rateBar: !prev.rateBar }));
            }}
          >
            <IconDiv variants={TabVari} whileHover="hover" whileTap="tap">
              {videoVal.playbackRate}X
            </IconDiv>
            {isBarTabs.rateBar ? (
              <RateBar>
                {[0.5, 0.75, 1, 1.25, 1.5, 2].map((rate) => {
                  return (
                    <Rate
                      whileHover={{ backgroundColor: "#dfdede" }}
                      onClick={() => {
                        playBackChangeHandler(rate);
                      }}
                      key={rate}
                    >
                      {rate}x
                    </Rate>
                  );
                })}
              </RateBar>
            ) : null}
          </RateTab>
          <IconDiv variants={TabVari} whileHover="hover" whileTap="tap">
            <Img src={pip} alt="no" onClick={pipHandler} />
          </IconDiv>
          <RateTab
            onClick={() => {
              setIsBarTabs((prev) => ({ ...prev, ccBar: !prev.ccBar }));
            }}
          >
            <IconDiv variants={TabVari} whileHover="hover" whileTap="tap">
              {videoVal.cc ? (
                <Icon icon={faClosedCaptioning} />
              ) : (
                <Icon icon={regular} />
              )}
            </IconDiv>
            {isBarTabs.ccBar ? (
              <RateBar style={{ bottom: "30px" }}>
                <Cc
                  whileHover={{ backgroundColor: "#dfdede" }}
                  onClick={ccTrue}
                >
                  켜기
                </Cc>
                <Cc
                  whileHover={{ backgroundColor: "#dfdede" }}
                  onClick={ccFalse}
                >
                  끄기
                </Cc>
              </RateBar>
            ) : null}
          </RateTab>
          <GearTab
            onClick={() => {
              setIsBarTabs((prev) => ({ ...prev, gearBar: !prev.gearBar }));
            }}
          >
            {isBarTabs.gearBar ? (
              <GearBar style={{ bottom: "30px" }}>
                <div>화질</div>
                {videoRef?.getInternalPlayer("hls")?.levels.map((level, id) => {
                  return (
                    <Cc
                      whileHover={{ backgroundColor: "#dfdede" }}
                      onClick={() => {
                        onChangeBitrate(id);
                      }}
                      key={id}
                      value={id}
                    >
                      {level.bitrate}px
                    </Cc>
                  );
                })}
              </GearBar>
            ) : null}
            <IconDiv variants={TabVari} whileHover="hover" whileTap="tap">
              <Icon icon={faGear} />
            </IconDiv>
          </GearTab>
          <IconDiv variants={TabVari} whileHover="hover" whileTap="tap">
            <Icon icon={faExpand} onClick={fullHandler} />
          </IconDiv>
        </IconSecTab>
      </ControlTab>
    </BarWarpper>
  );
}
