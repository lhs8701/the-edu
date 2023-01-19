import { Suspense, useEffect } from "react";
import { useQueries, useQuery } from "react-query";
import { useParams } from "react-router";
import styled from "styled-components";
import { courseApi } from "../api/courseApi";
import CourseDetail from "../components/course/CourseDetail";
import CourseIntro from "../components/course/CourseIntro";
import CoursePayment from "../components/course/CoursePayment";
import { dummyCourseInfo } from "../dummy";

const DividerBox = styled.div`
  display: flex;
  min-height: 80vh;
  margin-bottom: 50px;
`;

export default function CoursePage() {
  const dummycourseInfo = dummyCourseInfo;
  // const { courseId } = useParams();
  const courseId = 1;
  const courseInfo = useQuery(
    ["courseDetailInfo", courseId],
    () => {
      return courseApi(courseId);
    },
    {
      enabled: !!courseId,
      onSuccess: () => {
        console.log("성공");
      },
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  return (
    <div>
      <Suspense fallback={<div>로딩중</div>}>
        <CourseIntro courseInfo={courseInfo?.data} />
        <DividerBox>
          <CourseDetail courseInfo={dummycourseInfo} />
          <CoursePayment
            title={dummycourseInfo?.courseInfo?.title}
            teacher={dummycourseInfo?.courseInfo?.teacher}
            purchaseOption={dummycourseInfo.coursePurchaseInfo}
          />
        </DividerBox>
      </Suspense>
    </div>
  );
}
