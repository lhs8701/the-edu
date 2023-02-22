import * as React from "react";
import { useTheme } from "@mui/material/styles";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Label,
  ResponsiveContainer,
  BarChart,
  CartesianGrid,
  Tooltip,
  Legend,
  Bar,
} from "recharts";
import Title from "./Title";

// Generate Sales Data

export default function Chart({ money, title }) {
  const theme = useTheme();
  function createData(time, amount) {
    return { time, amount };
  }
  const [data, setData] = React.useState([]);
  const [barData, setBarData] = React.useState([]);
  React.useEffect(() => {
    let total = 0;
    let list = [];
    let barList = [];
    Object.values(money)[0].map((month, idx) => {
      if (month === null) {
        total += 0;
        money = 0;
      } else {
        total += Number(month);
        money = month;
      }

      list.push(createData(idx + "월", total));
      barList.push(createData(idx + "월", money));
    });
    setData(list);
    setBarData(barList);
  }, []);

  return (
    <React.Fragment>
      <Title>{title}년 동안의 수익 증가량</Title>
      <ResponsiveContainer>
        <LineChart
          data={data}
          height={150}
          margin={{
            top: 16,
            right: 16,
            bottom: 0,
            left: 24,
          }}
        >
          <XAxis
            dataKey="time"
            stroke={theme.palette.text.secondary}
            style={theme.typography.body2}
          />
          <YAxis
            stroke={theme.palette.text.secondary}
            style={theme.typography.body2}
          >
            <Label
              angle={270}
              position="left"
              style={{
                textAnchor: "middle",
                fill: theme.palette.text.primary,
                ...theme.typography.body1,
              }}
            >
              수익
            </Label>
          </YAxis>
          <Line
            isAnimationActive={false}
            type="monotone"
            dataKey="amount"
            stroke={theme.palette.primary.main}
            dot={false}
          />
        </LineChart>
      </ResponsiveContainer>
      <Title>{title}년 월 별 수익</Title>
      <ResponsiveContainer>
        <BarChart width={730} height={250} data={barData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="time" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey="amount" fill="#8884d8" />
        </BarChart>
      </ResponsiveContainer>
    </React.Fragment>
  );
}
