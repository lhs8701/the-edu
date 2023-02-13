import { Box, Button, Grid } from "@mui/material";
import React, { useState } from "react";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { uploadImageApi } from "../../../api/creatorApi";
import { createEventApi } from "../../../api/eventApi";
import { getAdminAccessTokenSelector } from "../../../atom";
import { ADMIN_BAR_LIST } from "../../../static";
import { CssTextField } from "../../creator/Outline";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

const UploadTab = styled(Box)`
  display: flex;
  justify-content: center;
  margin: 15px;
`;

const Img = styled.img`
  width: 500px;
  height: 400px;
`;

export default function UploadEvent({}) {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const [img, setImg] = useState({ url: false, file: false });
  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();

  const upload = (e) => {
    uploadImageApi(e.target.files[0], accessToken)
      .then(({ data }) => {
        setImg({ file: e.target.files[0], url: data.originalFilePath });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const uploadEvent = () => {
    createEventApi(accessToken, {
      title: title,
      content: content,
      startDate: startDate,
      endDate: endDate,
      bannerImage: img.url,
    });
  };

  return (
    <div>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[4].list[1].name} />
      <Grid container spacing={2}>
        <Grid item xs={6}>
          <CssTextField
            fullWidth
            type="text"
            id="title"
            name="title"
            label="제목"
            size="small"
            value={title}
            onChange={(e) => {
              setTitle(e.target.value);
            }}
          />
        </Grid>
        <Grid item xs={12}>
          <CssTextField
            fullWidth
            multiline
            type="text"
            id="content"
            name="content"
            rows={4}
            label="세부 내용"
            size="small"
            value={content}
            onChange={(e) => {
              setContent(e.target.value);
            }}
          />
        </Grid>
        <Grid item xs={4}>
          <CssTextField
            fullWidth
            type="date"
            id="price"
            name="price"
            size="small"
            value={startDate}
            onChange={(e) => {
              setStartDate(e.target.value);
            }}
          />
        </Grid>

        <Grid item xs={4}>
          <CssTextField
            fullWidth
            type="date"
            id="price"
            name="price"
            size="small"
            value={endDate}
            onChange={(e) => {
              setEndDate(e.target.value);
            }}
          />
        </Grid>
      </Grid>
      <br />
      <UploadTab>
        {img.file ? (
          <div>
            <Img src={URL.createObjectURL(img.file)} controls />
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
                setImg({ url: false, file: false });
              }}
            >
              취소
            </Button>
          </div>
        ) : (
          <>
            <Button
              sx={{
                width: 500,
                height: 400,
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
                accept=".jpg,.png,.jpeg"
                onChange={upload}
                multiple
                type="file"
              />
            </Button>
          </>
        )}
      </UploadTab>
      <Button onClick={uploadEvent}>이벤트 업로드</Button>
    </div>
  );
}
