import styled from "styled-components";
import ClassRankList from "../components/ClassRankList";
import MyClassCard from "../components/MyClassCard";
import { SlideNotice } from "../components/SlideNotice";
import {
  dummyCourseRank,
  dummyMyClassList,
  dummyProgrammingCourseRank,
} from "../dummy";

const MainWrapper = styled.div`
  width: 100%;
  height: 100%;
`;

const MyClassListBox = styled.div`
  margin-top: 40px;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  width: 100%;
  align-items: center;
  justify-items: center;
  margin-bottom: 50px;
`;

const ListTitle = styled.h1`
  font-weight: var(--weight-point);
  font-size: var(--size-rank-title);
  margin-bottom: 30px;
`;

export default function MainPage() {
  const weekRankList = dummyCourseRank;
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
    <MainWrapper>
      <SlideNotice />
      <ListTitle>나의 클래스</ListTitle>
      <MyClassListBox>
        <MyClassList />
      </MyClassListBox>
      <ClassRankList ranklist={weekRankList} />
      <ClassRankList ranklist={dummyProgrammingCourseRank} />
    </MainWrapper>
  );
}
