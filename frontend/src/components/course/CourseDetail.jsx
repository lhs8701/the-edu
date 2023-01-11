import { useRef, useState } from "react";
import styled from "styled-components";
import CourseCategory from "./CourseCategory";
import CourseImg from "./CourseImg";
import CourseInquire from "./CourseInquire";
import CourseNavBar from "./CourseNavBar";
import CourseReview from "./CourseReview";

const DetailWrapper = styled.div`
  width: 72%;
  height: 1000vh;
`;

const DetailBox = styled.div`
  width: 98%;
  height: 100%;
  margin-top: 30px;
`;

const RefDiv = styled.div`
  padding-top: 50px;
`;

export default function CourseDetail({ courseInfo }) {
  const [isTabStatus, setIsTabStatus] = useState(0);
  const courseRef = useRef([]);

  return (
    <DetailWrapper>
      <CourseNavBar
        refArr={courseRef}
        isTabStatus={isTabStatus}
        setIsTabStatus={setIsTabStatus}
      />
      <DetailBox ref={(component) => (courseRef.current[0] = component)}>
        <CourseImg images={courseInfo?.courseInfoImg} />
        <RefDiv ref={(component) => (courseRef.current[1] = component)}>
          <CourseCategory courseIdx={courseInfo.courseIndex} />
        </RefDiv>
        <RefDiv ref={(component) => (courseRef.current[2] = component)}>
          <CourseReview courseReviewInfo={courseInfo.courseReview} />
        </RefDiv>
        <RefDiv ref={(component) => (courseRef.current[3] = component)}>
          <CourseInquire courseInquireInfo={courseInfo.CourseInquire} />
        </RefDiv>
      </DetailBox>
    </DetailWrapper>
  );
}
