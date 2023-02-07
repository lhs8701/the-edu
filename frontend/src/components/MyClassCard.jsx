import { motion } from "framer-motion";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  PieChart,
  Pie,
  ResponsiveContainer,
  Cell,
  RadialBarChart,
  PolarAngleAxis,
  RadialBar,
} from "recharts";
import styled from "styled-components";
import { PROCESS_MAIN_URL } from "../static";

const ClassCard = styled.div`
  width: 85%;
  height: 230px;
  box-shadow: 0 1px 1px rgb(0 0 0 / 16%), 0 2px 14px rgb(0 0 0 / 16%);
  border-radius: var(--size-border-radius);
  overflow: hidden;
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  box-sizing: border-box;
`;

const ChartContainer = styled(ResponsiveContainer)`
  position: absolute;
  top: 0;
`;
const colors = ["#FFFFFF", "#FF5454"];

const ChartTitle = styled.p`
  font-weight: var(--weight-middle);
  font-size: var(--size-card-title);
`;

const UnitTitle = styled.p`
  font-weight: var(--weight-thin);
  font-size: var(--size-card-any);
  margin-top: 5px;
`;

const UnitRate = styled.p`
  font-weight: var(--weight-middle);
  font-size: 1.3rem;
`;

const TitleTab = styled(motion.div)`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  cursor: pointer;
  z-index: 10;
`;

const BottomTab = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const ProgressRate = styled.p`
  font-weight: var(--weight-thin);
  font-size: var(--size-card-any);
`;

const RateNum = styled.p`
  font-weight: var(--weight-middle);
  font-size: 1rem;
  margin-top: 3px;
  cursor: pointer;
  z-index: 10;
`;

const GoTo = styled(Link)`
  font-size: 18px;
  text-decoration: none;
  z-index: 10;
  font-weight: var(--weight-point);
  color: ${(props) =>
    props.mouse ? "var(--color-primary)" : "var(--color-text)"};
  &:hover {
    color: var(--color-primary);
    font-weight: var(--weight-middle);
  }
  cursor: pointer;
`;

const LinkDiv = styled.div`
  cursor: pointer;
`;

function easeOutExpo(t) {
  return t === 1 ? 1 : 1 - Math.pow(2, -10 * t);
}

export default function MyClassCard({ info, data, progressRatio }) {
  const [count, setCount] = useState(0);
  const frameRate = 1000 / 60;
  const totalFrame = Math.round(2000 / frameRate);
  const navigate = useNavigate();

  useEffect(() => {
    let currentNumber = 0;
    const counter = setInterval(() => {
      const progress = easeOutExpo(++currentNumber / totalFrame);
      setCount(Math.round(progressRatio * progress));
      if (progress === 1) {
        clearInterval(counter);
      }
    }, frameRate);
  }, []);

  const UnitTitleTab = () => {
    return (
      <TitleTab
        whileHover={{ x: 10 }}
        onClick={() => {
          navigate(`${PROCESS_MAIN_URL.COURSES}/${info?.courseId}`);
        }}
      >
        <div>
          <ChartTitle>{info?.title}</ChartTitle>
          <UnitTitle>&nbsp;{info?.nextUnitInfo?.title}</UnitTitle>
        </div>
      </TitleTab>
    );
  };

  const UnitProgressTab = () => {
    return (
      <BottomTab>
        <div>
          <ProgressRate>학습 상황</ProgressRate>
          <RateNum>
            {info?.completedUnits} / {info?.entireUnits}
          </RateNum>
        </div>
        <GoTo to={PROCESS_MAIN_URL.COURSES + "/" + info?.courseId + "/lobby"}>
          학습 하기
        </GoTo>
      </BottomTab>
    );
  };

  return (
    <ClassCard>
      <UnitTitleTab />
      <UnitProgressTab />
      <ChartContainer width="100%" height="100%">
        <RadialBarChart
          width={100}
          height={100}
          cx="50%"
          cy="50%"
          innerRadius={40}
          outerRadius={60}
          barSize={50}
          data={data}
          startAngle={90}
          endAngle={-270}
        >
          <PolarAngleAxis
            type="number"
            domain={[0, 100]}
            angleAxisId={0}
            tick={false}
          />
          <RadialBar background clockWise dataKey="value" fill="#FF5454" />
          <text x="50%" y="50%" textAnchor="middle" dominantBaseline="middle">
            {count}%
          </text>
        </RadialBarChart>
      </ChartContainer>
    </ClassCard>
  );
}
