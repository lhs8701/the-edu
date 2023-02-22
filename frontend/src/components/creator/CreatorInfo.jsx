import { Grid, Paper } from "@mui/material";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { getCreatorStatusApi } from "../../api/creatorApi";
import { getAccessTokenSelector } from "../../atom";
import { CREATOR_BAR_LIST } from "../../static";
import Chart from "../dashboard/Chart";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import Deposits from "../dashboard/Deposits";

export default function CreatorInfo() {
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const [status, setStatus] = useState();

  const getCreatorStatus = () => {
    getCreatorStatusApi(accessToken)
      .then(({ data }) => {
        console.log(data);
        setStatus(data);
      })
      .catch((err) => {
        alert(err);
      });
  };

  useEffect(getCreatorStatus, []);
  return (
    <Grid container xs={12}>
      <Grid item xs={12}>
        <DashboardTitleTab title={CREATOR_BAR_LIST.list[0].creator[0].name} />
      </Grid>
      <Grid item xs={3} mb={3}>
        <Paper
          sx={{
            p: 2,
            display: "flex",
            flexDirection: "column",
            height: 240,
          }}
        >
          <Deposits />
        </Paper>
      </Grid>
      <Grid item xs={12}>
        <Paper
          sx={{
            p: 2,
            display: "flex",
            flexDirection: "column",
            height: 240,
          }}
        >
          <Chart />
        </Paper>
      </Grid>
    </Grid>
  );
}
