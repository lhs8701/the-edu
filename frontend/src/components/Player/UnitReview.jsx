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

export default function UnitReview({ unitId }) {
  const [comment, setComment] = useState("");

  const [thumb, setThumb] = useState();
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const getFeedback = () => {
    getFeedbackApi(accessToken, unitId)
      .then(({ data }) => {
        console.log(data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const postFeedback = () => {
    postFeedbackApi(accessToken, unitId, thumb, comment)
      .then(({}) => {
        console.log("fd");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(getFeedback, []);

  const alreadyThumbComponent = ({ status }) => {
    let icon;
    if (status) {
      icon = ThumbDownAltIcon;
    } else {
      icon = ThumbDownOffAltIcon;
    }
    return (
      <Icon
        sx={{ height: "3rem", width: "3rem", cursor: "pointer" }}
        onClick={postFeedback}
        component={icon}
      />
    );
  };
  const noneThumbComponent = () => {
    return (
      <IconTab>
        <Icon
          sx={{ height: "3rem", width: "3rem", cursor: "pointer" }}
          onClick={() => {
            setThumb(true);
          }}
          component={ThumbUpOffAltIcon}
        />
        <Icon
          sx={{ height: "3rem", width: "3rem", cursor: "pointer" }}
          onClick={() => {
            setThumb(false);
          }}
          component={ThumbDownOffAltIcon}
        />
      </IconTab>
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

      {thumb ? <alreadyThumbComponent /> : <noneThumbComponent />}

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
