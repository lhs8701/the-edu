import { Grid, Paper } from "@mui/material";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { getAllProfitApi } from "../../api/adminApi";
import { getAdminAccessTokenSelector } from "../../atom";
import { ADMIN_BAR_LIST } from "../../static";
import Chart from "../dashboard/Chart";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import Deposits from "../dashboard/Deposits";

export default function CreatorsProfit() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const [status, setStatus] = useState();

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
  console.log(status);
  return (
    <Grid container xs={12}>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[3].list[1].name} />
      {status?.map((creator) => {
        return (
          <Grid container xs={12}>
            <Grid item xs={12}>
              크리에이터 이름
            </Grid>
            <Grid item xs={2.2} mb={3}>
              <Paper
                sx={{
                  p: 2,
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "center",
                  alignItems: "center",
                  height: 200,
                }}
              >
                <Deposits title={"전체 수익"} amount={status?.totalProfit} />
              </Paper>
            </Grid>
            {status?.map((course) => {
              <Grid item xs={2.2} mb={3}>
                <Paper
                  sx={{
                    p: 2,
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center",
                    height: 200,
                  }}
                >
                  <Deposits title={"전체 수익"} amount={status?.totalProfit} />
                </Paper>
              </Grid>;
            })}
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
      })}
    </Grid>
  );
}
