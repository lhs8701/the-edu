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
`;

const BtnTab = styled.div`
  margin-top: 15px;
  width: 90%;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const Form = styled.div`
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;
`;

const Input = styled.textarea`
  width: 90%;
  height: 10vh;
`;
const ThumbBox = styled.div`
  background-color: pink;
`;
export default function UnitReview({ unitId }) {
  const [comment, setComment] = useState("");
  const [thumbUp, setThumbUp] = useState(false);
  const [thumbDown, setThumbDown] = useState(false);
  const [clicked, setClicked] = useState(false);
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const getFeedback = () => {
    console.log("재 갱신");
    getFeedbackApi(accessToken, unitId)
      .then(({ data }) => {
        console.log(data);
        if (data.thumbsUp) {
          setThumbUp(true);
          setThumbDown(false);
        } else {
          setThumbDown(true);
          setThumbUp(false);
        }
      })
      .catch((err) => {
        if (err.response.status !== 404) {
          alert("서버 오류입니다.");
        }
      });
  };

  const postFeedback = (upAndDown) => {
    let thumb;
    if (upAndDown) {
      thumb = true;
    } else {
      thumb = false;
    }
    postFeedbackApi(accessToken, unitId, thumb, comment)
      .then(({}) => {})
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(getFeedback, [clicked]);

  const ThumbUpComponent = () => {
    let icon;
    if (thumbUp) {
      icon = ThumbUpIcon;
    } else {
      icon = ThumbUpOffAltIcon;
    }
    return (
      <Icon
        sx={{ height: "3rem", width: "3rem", cursor: "pointer" }}
        onClick={() => {
          setClicked((prev) => !prev);
          postFeedback(true);
        }}
        component={icon}
      />
    );
  };

  const ThumbDownComponent = () => {
    let icon;
    if (thumbDown) {
      icon = ThumbDownAltIcon;
    } else {
      icon = ThumbDownOffAltIcon;
    }
    return (
      <Icon
        sx={{ height: "3rem", width: "3rem", cursor: "pointer" }}
        onClick={() => {
          setClicked((prev) => !prev);
          postFeedback(false);
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

  return (
    <Wrapper>
      <SideTitleAlignCenter>강의가 어떠셨나요?</SideTitleAlignCenter>
      <IconTab>
        <ThumbUpComponent />
        <ThumbDownComponent />
      </IconTab>
      <Form>
        <Input
          value={comment}
          onChange={(e) => {
            setComment(e.target.value);
          }}
          placeholder="개선사항을 적어주세요!"
        />
        <BtnTab>
          <div />
          <button
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 1 }}
            onClick={postFeedback}
          >
            보내기
          </button>
        </BtnTab>
      </Form>
    </Wrapper>
  );
}
