import { motion } from "framer-motion";
import styled from "styled-components";
import ThumbDownAltIcon from "@mui/icons-material/ThumbDownAlt";
import ThumbDownOffAltIcon from "@mui/icons-material/ThumbDownOffAlt";
import ThumbUpOffAltIcon from "@mui/icons-material/ThumbUpOffAlt";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import { Icon } from "@mui/material";
import { SideTitle, Wrapper } from "../../style/PlayerSideBarCss";
import { useRecoilValue } from "recoil";
import { getAccessTokenSelector } from "../../atom";
import { getFeedbackApi, postFeedbackApi } from "../../api/feebackApi";

const SideTitleAlignCenter = styled(SideTitle)`
  text-align: center;
  margin-top: 40%;
`;

const ReviewWrapper = styled(Wrapper)`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export default function UnitReview({ unitId }) {
  const [comment, setComment] = useState("");
  const [thumbsUp, setThumbsUp] = useState(false);
  const [thumbsDown, setThumbsDown] = useState(false);
  const [clicked, setClicked] = useState(false);
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const getFeedback = () => {
    getFeedbackApi(accessToken, unitId)
      .then(({ data }) => {
        setThumbsUp(data.thumbsUp);
        setThumbsDown(data.thumbsDown);
      })
      .catch((err) => {
        if (err.response.status !== 404) {
          alert("서버 오류입니다.");
        }
      });
  };

  const postFeedback = (thumbsUp, thumbsDown) => {
    console.log("이거 보냄", thumbsUp, thumbsDown);
    postFeedbackApi(accessToken, unitId, thumbsUp, thumbsDown)
      .then(({}) => {})
      .catch((err) => {
        console.log(err);
      });
  };

  const ThumbUpComponent = () => {
    let icon;
    if (thumbsUp) {
      icon = ThumbUpIcon;
    } else {
      icon = ThumbUpOffAltIcon;
    }
    return (
      <Icon
        sx={{ height: "3rem", width: "3rem", cursor: "pointer" }}
        onClick={() => {
          if (thumbsUp) {
            postFeedback(false, false);
            setThumbsUp(false);
          } else {
            postFeedback(true, false);
            setThumbsUp(true);
            setThumbsDown(false);
          }
        }}
        component={icon}
      />
    );
  };

  const ThumbDownComponent = () => {
    let icon;
    if (thumbsDown) {
      icon = ThumbDownAltIcon;
    } else {
      icon = ThumbDownOffAltIcon;
    }
    return (
      <Icon
        sx={{ height: "3rem", width: "3rem", cursor: "pointer" }}
        onClick={() => {
          if (thumbsDown) {
            postFeedback(false, false);
            setThumbsDown(false);
          } else {
            postFeedback(false, true);
            setThumbsDown(true);
            setThumbsUp(false);
          }
        }}
        component={icon}
      />
    );
  };

  const IconTab = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-around;
    margin: 50px 0px;
  `;

  useEffect(getFeedback, []);

  return (
    <Wrapper>
      <SideTitleAlignCenter>강의가 어떠셨나요?</SideTitleAlignCenter>
      <IconTab>
        <ThumbUpComponent />
        <ThumbDownComponent />
      </IconTab>
    </Wrapper>
  );
}
