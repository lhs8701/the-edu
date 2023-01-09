import { useState } from "react";
import styled from "styled-components";
import CourseCategory from "./CourseCategory";
import CourseImg from "./CourseImg";
import CourseInquire from "./CourseInquire";
import CourseNavBar from "./CourseNavBar";
import CourseReview from "./CourseReview";
import { images } from "../../dummy";

const DetailWrapper = styled.div`
  width: 72%;
`;

const DetailBox = styled.div`
  width: 98%;
  height: 100%;
  margin-top: 15px;
`;

export default function CourseDetail() {
  const [isTabStatus, setIsTabStatus] = useState(0);

  return (
    <DetailWrapper>
      <CourseNavBar isTabStatus={isTabStatus} setIsTabStatus={setIsTabStatus} />
      <DetailBox>
        <CourseImg images={images} />
        <CourseCategory />
        <CourseReview />
        <CourseInquire />
      </DetailBox>
    </DetailWrapper>
  );
}
