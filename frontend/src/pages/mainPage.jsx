import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getLoginState } from "../atom";
import ClassRankList from "../components/ClassRankList";
import MyClassCard from "../components/MyClassCard";
import { SlideNotice } from "../components/SlideNotice";
import {
  dummyCourseRank,
  dummyMyClassList,
  dummyProgrammingCourseRank,
} from "../dummy";
import { Wrapper } from "../style/CommonCss";

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
  const loginState = useRecoilValue(getLoginState);

  const MyClassList = () => {
    return dummyMyClassList.map((course, index) => {
      const progressRatio = Math.round(
        (course?.nowUnitCnt / course?.totalUnitCnt) * 100
      );
      const data = [
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
    <Wrapper>
      <SlideNotice />
      {loginState ? (
        <>
          <ListTitle>나의 클래스</ListTitle>{" "}
          <MyClassListBox>
            <MyClassList />
          </MyClassListBox>
        </>
      ) : null}

      <ClassRankList ranklist={weekRankList} />
      <ClassRankList ranklist={dummyProgrammingCourseRank} />
    </Wrapper>
  );
}
