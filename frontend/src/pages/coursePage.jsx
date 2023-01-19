import { Suspense } from "react";
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
  // const courseInfo = dummyCourseInfo;
  // const { courseId } = useParams();
  const courseId = 1;
  const courseInfo = useQuery(
    ["courseDetailInfo", courseId],
    () => {
      return courseApi(courseId);
    },
    {
      enabled: !!courseId,
      retry: 1,
      // onSuccess: (data) => {},
    }
  );

  return (
    <div>
      <Suspense fallback={<div>로딩중</div>}>
        <CourseIntro courseInfo={courseInfo.data} />
        {/* <DividerBox>
          <CourseDetail courseInfo={courseInfo.data} />
          <CoursePayment
            title={courseInfo?.data?.title}
            teacher={courseInfo?.data?.instructor}
            purchaseOption={courseInfo.data}
          />
        </DividerBox> */}
      </Suspense>
    </div>
  );
}
