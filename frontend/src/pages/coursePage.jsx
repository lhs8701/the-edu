import { Suspense, useEffect } from "react";
import { useQueries, useQuery } from "react-query";
import { useNavigate, useParams } from "react-router";
import styled from "styled-components";
import {
  courseApi,
  getcourseInquiriessApi,
  getcourseReviewsApi,
  getCourseTicketsApi,
  getCurriculumApi,
} from "../api/courseApi";
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
  const { courseId } = useParams();
  const navigate = useNavigate();
  const info = useQuery(
    ["courseDetailInfo", courseId],
    () => {
      return courseApi(courseId);
    },
    {
      enabled: !!courseId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );
  const paymentInfo = useQuery(
    ["coursePaymentInfo", courseId],
    () => {
      return getCourseTicketsApi(courseId);
    },
    {
      enabled: !!courseId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );
  useQuery(
    ["courseReviews", courseId],
    () => {
      return getcourseReviewsApi(courseId);
    },
    {
      enabled: !!courseId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  useQuery(
    ["courseInquiries", courseId],
    () => {
      return getcourseInquiriessApi(courseId);
    },
    {
      enabled: !!courseId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  useQuery(
    ["courseCurriculum", courseId],
    () => {
      return getCurriculumApi(courseId);
    },
    {
      enabled: !!courseId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  if (info.error) {
    navigate("/error");
  }

  return (
    <div>
      <Suspense fallback={<div>로딩중</div>}>
        <CourseIntro courseId={courseId} />
        <DividerBox>
          <CourseDetail courseId={courseId} />
          {paymentInfo?.data?.data && (
            <CoursePayment
              ticketInfo={paymentInfo?.data?.data}
              title={info?.data?.title}
              teacher={info?.data?.instructor}
              courseId={courseId}
            />
          )}
        </DividerBox>
      </Suspense>
    </div>
  );
}
