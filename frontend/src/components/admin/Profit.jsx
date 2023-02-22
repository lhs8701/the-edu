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

  useEffect(getSiteStatus, []);

  return (
    <Grid container xs={12}>
      <Grid item xs={12}>
        <DashboardTitleTab title={ADMIN_BAR_LIST.list[3].list[2].name} />
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
      <Grid item xs={12} mb={1}>
        <div>년도 별 전체 수익</div>
      </Grid>

      {status?.monthlyProfit?.map((year, idx) => {
        const title = Object.keys(year);
        return (
          <Grid key={idx} item xs={12} mb={1}>
            <Paper
              sx={{
                p: 2,
                display: "flex",
                flexDirection: "column",
                height: 600,
              }}
            >
              <Chart title={title[0]} money={year} />
            </Paper>
          </Grid>
        );
      })}
    </Grid>
  );
}
