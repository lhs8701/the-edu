import { Box, Button, Grid, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { requestCreatorApi } from "../../api/creatorApi";
import { getAccessTokenSelector, getCreatorIdSelector } from "../../atom";
import { CREATOR_BAR_LIST, PROCESS_MAIN_URL } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import { CssTextField } from "./uploadCourse/Outline";

const RegistBtn = styled.button``;

export default function ResearchBox() {
  const navigate = useNavigate();
  const [career, setCareer] = useState("");
  const [subject, setSubject] = useState("");
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const creatorId = useRecoilValue(getCreatorIdSelector);

  const requestCreator = (e) => {
    e.preventDefault();
    if (window.confirm(`크리에이터 신청을 하시겠습니까?`)) {
      requestCreatorApi(accessToken, subject, career)
        .then(() => {
          alert(
            "크리에이터 신청이 되었습니다. 추후 이메일로 결과에 대해 통지해드리겠습니다."
          );
        })
        .catch((err) => {
          if (err.response.data.code === -1002) {
            alert("이미 신청을 했습니다.");
          }
          if (err.response.data.code === -6010) {
            alert("본인인증이 필요합니다.");
            navigate(
              "/" +
                PROCESS_MAIN_URL.MYPAGE.DEFAULT +
                PROCESS_MAIN_URL.MYPAGE.REVISE
            );
          }
        });
    }
  };

  useEffect(() => {
    if (creatorId > 0) {
      alert("크리에이터 권환이 없습니다.");
      navigate(CREATOR_BAR_LIST.list[0].creator[0].url);
    }
  }, []);

  return (
    <>
      <DashboardTitleTab title={CREATOR_BAR_LIST.list[0].creator[1].name} />
      <Box component="form">
        <div>검토 후 이메일로 연락을 드리겠습니다.</div>
        <br />
        <Grid container spacing={2} direction="row" justifyContent="flex-start">
          <Grid item xs={12}>
            <CssTextField
              size="small"
              fullWidth
              value={subject}
              onChange={(e) => {
                setSubject(e.target.value);
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
          <Grid item justifyContent="flex-end">
            <RegistBtn onClick={requestCreator}>크리에이터 신청</RegistBtn>
          </Grid>
        </Grid>
      </Box>
    </>
  );
}
