import { faL } from "@fortawesome/free-solid-svg-icons";
import { Box, Button, CircularProgress, Grid, TextField } from "@mui/material";
import React, { useState } from "react";
import { useNavigate, useOutletContext } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { uploadVideoApi } from "../../../api/creatorApi";
import { getAccessTokenSelector } from "../../../atom";
import { ProgressBarDiv } from "./CourseInfoUpload";

const UploadTab = styled(Box)`
  display: flex;
  justify-content: center;
  margin: 15px;
`;

const Video = styled.video`
  width: 350px;
  height: 200px;
`;

const UnitInfoOutline = React.memo(function UnitInfoOutline({
  fileUrl,
  setFileUrl,
  video,
  setVideo,
  reviseUnitVideo,
  setIsUpload,
  isUpload,
}) {
  const accessToken = useRecoilValue(getAccessTokenSelector);

  // const [isUpload, setIsUpload] = useState(false);
  const upload = (e) => {
    setIsUpload(true);
    setVideo(e.target.files[0]);
    uploadVideoApi(accessToken, e.target.files[0])
      .then(({ data }) => {
        if (data.code === -7001) {
          setIsUpload(false);
        } else {
          setFileUrl(data.filePath);
          reviseUnitVideo(data.filePath, e.target.files[0]);
        }
      })
      .catch((err) => {
        alert(err);
        setIsUpload(false);
      });
  };

  return (
    <UploadTab>
      {fileUrl ? (
        <div>
          <Video src={URL.createObjectURL(video)} controls />
          <br />
          <Button
            sx={{
              backgroundColor: "var(--color-box-gray)",
              "&:hover": {
                backgroundColor: "#b1b1b1",
              },
            }}
            variant="contained"
            onClick={() => {
              setFileUrl("");
              setVideo();
              setIsUpload(false);
            }}
          >
            취소
          </Button>
        </div>
      ) : isUpload ? (
        <ProgressBarDiv>
          <CircularProgress
            sx={{
              color: "var(--color-primary)",
            }}
          />
        </ProgressBarDiv>
      ) : (
        <Button
          sx={{
            width: 300,
            height: 200,
            backgroundColor: "var(--color-box-gray)",
            "&:hover": {
              backgroundColor: "#b1b1b1",
            },
          }}
          variant="contained"
          component="label"
        >
          Upload
          <input hidden accept=".mp4" onChange={upload} multiple type="file" />
        </Button>
      )}
    </UploadTab>
  );
});

export default React.memo(UnitInfoOutline);
