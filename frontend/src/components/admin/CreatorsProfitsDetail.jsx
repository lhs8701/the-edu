import { Grid, Paper } from "@mui/material";
import { useLocation } from "react-router";
import Chart from "../dashboard/Chart";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import Deposits from "../dashboard/Deposits";

export default function CreatorsProfitDetail() {
  const { state } = useLocation();

  return (
    <Grid container xs={12}>
      <Grid item xs={12}>
        <DashboardTitleTab title={`${state.creatorName} 강사의 수익`} />
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
          <Deposits title={"전체 수익"} amount={state?.totalProfit} />
        </Paper>
      </Grid>
      {state?.courseStatus?.map((course, idx) => {
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
