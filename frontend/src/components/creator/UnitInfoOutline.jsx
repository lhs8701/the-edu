import { Box, Button, Grid, TextField } from "@mui/material";
import { useState } from "react";
import { useNavigate, useOutletContext } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { uploadVideoApi } from "../../api/creatorApi";
import { getAccessTokenSelector } from "../../atom";

const UploadTab = styled(Box)`
  display: flex;
  justify-content: center;
  margin: 15px;
`;

const Video = styled.video`
  width: 350px;
  height: 200px;
`;

export default function UnitInfoOutline() {
  const fileUrl = useOutletContext()[0];
  const setFileUrl = useOutletContext()[1];
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const [video, setVideo] = useState();
  const upload = (e) => {
    setVideo(e.target.files[0]);
    uploadVideoApi(accessToken, e.target.files[0]).then(({ data }) => {
      setFileUrl(data.filePath);
    });
  };
  console.log(fileUrl);
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
            }}
          >
            취소
          </Button>
        </div>
      ) : (
        <>
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
            <input
              hidden
              accept=".mp4"
              onChange={upload}
              multiple
              type="file"
            />
          </Button>
        </>
      )}
    </UploadTab>
  );
}
