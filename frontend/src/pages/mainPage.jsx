import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getRankingApi } from "../api/courseApi";
import { getOngoingEventApi } from "../api/eventApi";
import {
  getAccessTokenSelector,
  getLoginState,
  getMemberIdSelector,
} from "../atom";
import ClassCard from "../components/ClassCard";
import MyClassCard from "../components/MyClassCard";
import { SlideNotice } from "../components/SlideNotice";
import { dummyCourseRank, dummyMyClassList } from "../dummy";
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

const ListWrapper = styled.div`
  margin-top: 25px;
  margin-bottom: 60px;
`;

const ClassListBox = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 200px;
`;

export default function MainPage() {
  const weekRankList = dummyCourseRank;
  const loginState = useRecoilValue(getLoginState);
  const memberId = useRecoilValue(getMemberIdSelector);
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const { data } = useQuery(
    ["rankList"],
    () => {
      return getRankingApi();
    },
    {
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const eventList = useQuery(
    ["eventList"],
    () => {
      return getOngoingEventApi();
    },
    {
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

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

  const ClassRankList = ({ rankList }) => {
    return (
      <ListWrapper>
        <ListTitle>{rankList.category} 클래스 랭킹</ListTitle>
        <ClassListBox>
          {rankList.courseList.map((course) => {
            return <ClassCard key={course.courseId} course={course} />;
          })}
        </ClassListBox>
      </ListWrapper>
    );
  };

  const CategoryRankComponent = ({ rankList }) => {
    return <ClassRankList rankList={rankList} />;
  };

  const CategoriesRankComponent = () => {
    return data?.data?.map((rankList, idx) => {
      return <CategoryRankComponent rankList={rankList} key={idx} />;
    });
  };

  const MyClassComponent = () => {
    return (
      <>
        <ListTitle>나의 클래스</ListTitle>{" "}
        <MyClassListBox>
          <MyClassList />
        </MyClassListBox>
      </>
    );
  };

  return (
    <Wrapper>
      {eventList?.data?.data && (
        <SlideNotice eventList={eventList?.data?.data} />
      )}
      {loginState && <MyClassComponent />}
      {data && <CategoriesRankComponent />}
    </Wrapper>
  );
}
