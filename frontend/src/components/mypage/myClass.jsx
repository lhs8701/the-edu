import { motion } from "framer-motion";
import { useState } from "react";
import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { myCourseApi } from "../../api/myPageApi";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
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

  const MyClassList = ({ myCourses }) => {
    return myCourses?.map((course, index) => {
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
    <MyPageBox>
      <MyPageTitle>나의 클래스</MyPageTitle>
      <MyPageContentBox>
        {myCourses?.data?.length === 0 ? (
          <h1>나의 강의가 없어요</h1>
        ) : (
          <>
            <MyClassNavBar
              isTabStatus={isTabStatus}
              setIsTabStatus={setIsTabStatus}
            />
            <MyClassListBox>
              <MyClassList myCourses={myCourses.data} />
            </MyClassListBox>
          </>
        )}
      </MyPageContentBox>
    </MyPageBox>
  );
}
