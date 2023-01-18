import { useQueries, useQuery } from "react-query";
import styled from "styled-components";
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
  const courseInfo = dummyCourseInfo;
  // const { data, isLoading, isError } = useQuery();
  return (
    <div>
      <CourseIntro courseInfo={courseInfo.courseInfo} />
      <DividerBox>
        <CourseDetail courseInfo={courseInfo} />
        <CoursePayment
          title={courseInfo?.courseInfo?.title}
          teacher={courseInfo?.courseInfo?.teacher}
          purchaseOption={courseInfo.coursePurchaseInfo}
        />
      </DividerBox>
    </div>
  );
}
