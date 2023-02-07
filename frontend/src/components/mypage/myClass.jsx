import { useState } from "react";
import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getCompletedApi, getOngingApi } from "../../api/courseApi";
import { myCourseApi } from "../../api/myPageApi";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
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
  const [isTabStatus, setIsTabStatus] = useState(1);
  const memberId = useRecoilValue(getMemberIdSelector);
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const myCourses = useQuery(
    ["myCourseList", memberId],
    () => {
      return myCourseApi(memberId, accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: (res) => {
        console.log(res);
      },
      onError: (err) => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const myOngoingCourses = useQuery(
    ["myOngoingCourseList", memberId],
    () => {
      return getOngingApi(accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: (res) => {},
      onError: (err) => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const myCompletedCourses = useQuery(
    ["myCompletedCourseList", memberId],
    () => {
      return getCompletedApi(accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: (res) => {},
      onError: (err) => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const Course = ({ courseInfo }) => {
    const progressRatio = Math.round(
      (courseInfo?.completedUnits / courseInfo?.entireUnits) * 100
    );
    const data = [
      {
        name: "Complete",
        value: progressRatio,
      },
    ];

    return (
      <MyClassCard
        info={courseInfo}
        data={data}
        progressRatio={progressRatio}
      />
    );
  };

  const MyClassList = ({ courses }) => {
    console.log(courses);
    return courses?.map((course) => {
      return <Course key={course?.courseId} courseInfo={course} />;
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
          {isTabStatus === 0 && (
            <MyClassList
              courses={[
                ...myOngoingCourses?.data?.data,
                ...myCompletedCourses?.data?.data,
              ]}
            />
          )}
          {isTabStatus === 1 && (
            <MyClassList courses={myOngoingCourses?.data?.data} />
          )}
          {isTabStatus === 2 && (
            <MyClassList courses={myCompletedCourses?.data?.data} />
          )}
        </MyClassListBox>
      </MyPageContentBox>
    </MyPageBox>
  );
}
