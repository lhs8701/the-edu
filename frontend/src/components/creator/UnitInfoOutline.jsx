import { Box, Button, Grid, TextField } from "@mui/material";
import { useState } from "react";
import { useNavigate, useOutletContext } from "react-router";
import styled from "styled-components";

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
  const file = useOutletContext()[0];
  const setFile = useOutletContext()[1];
  const upload = (e) => {
    setFile(e.target.files[0]);
  };

  return (
    <UploadTab>
      {file ? (
        <div>
          <Video src={URL.createObjectURL(file)} controls />
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
              setFile();
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
