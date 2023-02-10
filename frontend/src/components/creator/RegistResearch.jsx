import { Box, Button, Grid, TextField } from "@mui/material";
import { useState } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { requestCreatorApi } from "../../api/creatorApi";
import { getAccessTokenSelector } from "../../atom";
import { CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import { CssTextField } from "./Outline";

const RegistBtn = styled.button``;

export default function ResearchBox() {
  const navigate = useNavigate();
  const [career, setCareer] = useState("");
  const [category, setCategory] = useState("");
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const requestCreator = (e) => {
    e.preventDefault();
    requestCreatorApi(accessToken)
      .then(() => {
        alert("신청 완료");
      })
      .catch((err) => {
        if (err.response.data.code === -1002) {
          alert("이미 신청을 했습니다.");
        }
      });
  };
  return (
    <>
      <DashboardTitleTab title={CREATOR_BAR_LIST.list[0].creator[1].name} />
      <Box component="form">
        <Grid container spacing={2} direction="row" justifyContent="flex-start">
          <Grid item xs={12}>
            <CssTextField
              size="small"
              fullWidth
              value={category}
              onChange={(e) => {
                setCategory(e.target.value);
              }}
              label="개설하고 싶은 강좌 주제를 입력해주세요"
              variant="outlined"
            />
          </Grid>
          <Grid item xs={12}>
            <CssTextField
              size="medium"
              fullWidth
              value={career}
              onChange={(e) => {
                setCareer(e.target.value);
              }}
              multiline
              maxRows={10}
              rows={10}
              label="경력을 입력해주세요"
              variant="outlined"
            />
          </Grid>
          {/* <Grid item xs={12}>
            <CssTextField
              size="medium"
              fullWidth
              value={career}
              onChange={(e) => {
                setCareer(e.target.value);
              }}
              label="경력을 입력해주세요"
              variant="outlined"
            />
          </Grid> */}
          <Grid item justifyContent="flex-end">
            <RegistBtn onClick={requestCreator}>크리에이터 신청</RegistBtn>
          </Grid>
        </Grid>
      </Box>
    </>
  );
}
