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
      <Grid item xs={2.2} mb={4}>
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
      {status?.courseStatus?.map((course, idx) => {
        return (
          <Grid key={course.courseId} item xs={12} mb={2}>
            <DashboardTitleTab title={`"${course.title}" 강좌의 수익`} />
            <span>총 수강생 수: {course?.studentCount}</span>&nbsp;&nbsp;
            <span>환불한 수강생 수: {course?.cancelCount}</span>
            {course?.monthlyProfit?.map((year, idx) => {
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
      })}
    </Grid>
  );
}
