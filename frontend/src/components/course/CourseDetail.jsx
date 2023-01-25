import { useInView } from "framer-motion";
import { useEffect } from "react";
import { useRef, useState } from "react";
import { QueryClient } from "react-query";
import styled from "styled-components";
import { queryClient } from "../../App";
import CourseCategory from "./CourseCategory";
import CourseImg from "./CourseImg";
import CourseInquire from "./CourseInquire";
import CourseNavBar from "./CourseNavBar";
import CourseReview from "./CourseReview";

const DetailWrapper = styled.div`
  width: 72%;
`;

const DetailBox = styled.div`
  width: 98%;
  height: 100%;
  margin-top: 30px;
`;

const RefDiv = styled.div`
  padding-top: 50px;
`;

export default function CourseDetail({ courseId }) {
  const [isTabStatus, setIsTabStatus] = useState(0);

  // const courseRef = useRef([]);
  const imgRef = useRef(null);
  const cateRef = useRef(null);
  const reviewRef = useRef(null);
  const inquireRef = useRef(null);
  // const data = queryClient.getQueryData(["courseDetailInfo", courseId]);
  const refArr = [imgRef, cateRef, reviewRef, inquireRef];

  const viewArr = [
    useInView(imgRef),
    useInView(cateRef),
    useInView(reviewRef),
    useInView(inquireRef),
  ];

  // const viewArr = useMemo(() => {
  //   return [
  //     useInView(imgRef),
  //     useInView(cateRef),
  //     useInView(reviewRef),
  //     useInView(inquireRef),
  //   ];
  // }, [imgRef, cateRef, reviewRef, inquireRef]);

  const currentViewFinder = (viewArr) => {
    const result = [];
    viewArr.filter((status, idx) => {
      if (status) {
        result.push(idx);
      }
    });
    if (result.length === 0) {
      result.push(0);
    }
    return result[result.length - 1];
  };

  useEffect(() => {
    setIsTabStatus(currentViewFinder(viewArr));
  }, [viewArr]);

  return (
    <DetailWrapper>
      <div ref={imgRef} />
      <CourseNavBar
        refArr={refArr}
        isTabStatus={isTabStatus}
        setIsTabStatus={setIsTabStatus}
      />
      {/* <DetailBox ref={(component) => (courseRef.current[0] = component)}>
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
      </DetailBox> */}
      <DetailBox>
        {/* <CourseImg images={courseInfo?.courseInfoImg} /> */}
        <RefDiv ref={cateRef}>
          {/* <CourseCategory courseIdx={courseInfo.courseIndex} /> */}
        </RefDiv>
        <RefDiv ref={reviewRef}>
          <CourseReview courseId={courseId} />
        </RefDiv>
        <RefDiv ref={inquireRef}>
          <CourseInquire courseId={courseId} />
        </RefDiv>
      </DetailBox>
    </DetailWrapper>
  );
}
