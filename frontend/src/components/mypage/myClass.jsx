import { motion } from "framer-motion";
import { useState } from "react";
import styled from "styled-components";
import { dummyMyClassList } from "../../dummy";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";
import MyClassCard from "../MyClassCard";
import MyClassNavBar from "./MyClassNavBar";

const MyClassListBox = styled.div`
  margin-top: 40px;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-row-gap: 40px;
  width: 100%;
  align-items: center;
  justify-items: center;
  margin-bottom: 50px;
`;

export default function MyClass() {
  const [isTabStatus, setIsTabStatus] = useState(0);

  const MyClassList = () => {
    return dummyMyClassList.map((course, index) => {
      const progressRatio = Math.round(
        (course?.nowUnitCnt / course?.totalUnitCnt) * 100
      );
      const data = [
        {
          name: "Uncomplete",
          value: 100 - progressRatio,
        },
        {
          name: "Complete",
          value: progressRatio,
        },
      ];
      return (
        <MyClassCard info={course} data={data} progressRatio={progressRatio} />
      );
    });
  };

  return (
    <MyPageBox>
      <MyPageTitle>나의 클래스</MyPageTitle>
      <MyPageContentBox>
        <MyClassNavBar
          isTabStatus={isTabStatus}
          setIsTabStatus={setIsTabStatus}
        />
        <MyClassListBox>
          <MyClassList />
        </MyClassListBox>
      </MyPageContentBox>
    </MyPageBox>
  );
}
