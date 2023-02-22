import { Grid, Paper } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getAllProfitApi } from "../../api/adminApi";
import { getAdminAccessTokenSelector } from "../../atom";
import { ADMIN_BAR_LIST } from "../../static";
import Chart from "../dashboard/Chart";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import Deposits from "../dashboard/Deposits";

const CreatorBox = styled.div`
  border: 1px solid var(--color-box-gray);
  padding: 10px;
`;

export default function CreatorsProfit() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const [status, setStatus] = useState();
  const navigate = useNavigate();
  const getSiteStatus = () => {
    getAllProfitApi(accessToken)
      .then(({ data }) => {
        setStatus(data.creatorStatusList);
      })
      .catch((err) => {
        alert(err);
      });
  };

  useEffect(getSiteStatus, []);

  return (
    <Grid container xs={12}>
      <Grid item xs={12}>
        <DashboardTitleTab title={ADMIN_BAR_LIST.list[3].list[1].name} />
      </Grid>
      {status?.map((creator, idx) => {
        return (
          <CreatorBox>
            {creator.creatorName} 강사의 수익보기 &nbsp; &nbsp; &nbsp;&nbsp;
            &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
            <span>총 수익:{creator.totalProfit}</span>
            &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;
            <button
              onClick={() => {
                navigate(`${creator.creatorId}`, { state: creator });
              }}
            >
              이동
            </button>
          </CreatorBox>
        );
      })}
    </Grid>
  );
}
