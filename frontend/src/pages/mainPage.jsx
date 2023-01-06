import styled from "styled-components";
import ClassRankList from "../components/ClassRankList";
import { SlideNotice } from "../components/SlideNotice";
import { dummyCourseRank, dummyProgrammingCourseRank } from "../dummy";

const MainWrapper = styled.div`
  width: 100%;
  height: 100%;
`;

export default function MainPage() {
  const weekRankList = dummyCourseRank;
  return (
    <MainWrapper>
      <SlideNotice />
      <ClassRankList ranklist={weekRankList} />
      <ClassRankList ranklist={dummyProgrammingCourseRank} />
    </MainWrapper>
  );
}
