import { useRef, useState } from "react";
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
`;

const RefDiv = styled.div`
  padding-top: 80px;
`;

export default function CourseDetail({ courseInfo }) {
  const [isTabStatus, setIsTabStatus] = useState(0);
  const imgRef = useRef(null);
  const categoryRef = useRef(null);
  const reviewRef = useRef(null);
  const inquireRef = useRef(null);
  const refArr = [imgRef, categoryRef, reviewRef, inquireRef];

  return (
    <DetailWrapper>
      <CourseNavBar
        refArr={refArr}
        isTabStatus={isTabStatus}
        setIsTabStatus={setIsTabStatus}
      />
      <DetailBox>
        <RefDiv ref={refArr[0]}>
          <CourseImg images={courseInfo?.courseInfoImg} />
        </RefDiv>
        <RefDiv ref={refArr[1]}>
          <CourseCategory courseIdx={courseInfo.courseIndex} />
        </RefDiv>
        <RefDiv ref={refArr[2]}>
          <CourseReview courseReviewInfo={courseInfo.courseReview} />
        </RefDiv>
        <RefDiv ref={refArr[3]}>
          <CourseInquire courseInquireInfo={courseInfo.CourseInquire} />
        </RefDiv>
      </DetailBox>
    </DetailWrapper>
  );
}
