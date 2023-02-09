import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getLatestApi, getRankingApi } from "../api/courseApi";
import { getOngoingEventApi } from "../api/eventApi";
import {
  getAccessTokenSelector,
  getLoginState,
  getMemberIdSelector,
} from "../atom";
import ClassCard from "../components/ClassCard";
import MyClassCard from "../components/MyClassCard";
import { SlideNotice } from "../components/SlideNotice";
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
const NoneTitle = styled.h1`
  font-size: 1.5rem;
  font-weight: var(--weight-middle);
  text-align: center;
`;

export default function MainPage() {
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

  const recentList = useQuery(
    ["recentList", memberId],
    () => {
      return getLatestApi(accessToken);
    },
    {
      enabled: !!loginState,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

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

  const MyClassList = ({ recentList }) => {
    return recentList.map((course) => {
      const progressRatio = Math.round(
        (course?.completedUnits / course?.entireUnits) * 100
      );
      const data = [
        {
          name: "Complete",
          value: progressRatio,
        },
      ];
      return (
        <MyClassCard
          key={course.courseId}
          info={course}
          data={data}
          progressRatio={progressRatio}
        />
      );
    });
  };

  const NoneTitleComponent = () => {
    return (
      <NoneTitle>
        <br />
        최근 들은 강의가 없어요!
        <br />
        <br />
        <br />
        <br />
        <br />
      </NoneTitle>
    );
  };

  const MyClassComponent = ({ recentList }) => {
    return (
      <>
        <ListTitle>최근 들은 클래스</ListTitle>{" "}
        {recentList.length === 0 ? (
          <NoneTitleComponent />
        ) : (
          <MyClassListBox>
            <MyClassList recentList={recentList} />
          </MyClassListBox>
        )}
      </>
    );
  };

  return (
    <Wrapper>
      {eventList?.data?.data && (
        <SlideNotice eventList={eventList?.data?.data} />
      )}
      {recentList?.data?.data && (
        <MyClassComponent recentList={recentList?.data?.data} />
      )}
      {data && <CategoriesRankComponent />}
    </Wrapper>
  );
}
