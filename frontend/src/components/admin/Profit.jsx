import { Grid, Paper } from "@mui/material";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { getAllProfitApi } from "../../api/adminApi";
import { getAdminAccessTokenSelector } from "../../atom";
import { ADMIN_BAR_LIST } from "../../static";
import Chart from "../dashboard/Chart";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import Deposits from "../dashboard/Deposits";

export default function Profit() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const [status, setStatus] = useState();

  const getSiteStatus = () => {
    getAllProfitApi(accessToken)
      .then(({ data }) => {
        setStatus(data);
      })
      .catch((err) => {
        alert(err);
      });
  };
  console.log(status);

  useEffect(getSiteStatus, []);
  return (
    <Grid container xs={12}>
      <Grid item xs={12}>
        <DashboardTitleTab title={ADMIN_BAR_LIST.list[3].list[2].name} />
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
