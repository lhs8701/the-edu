import { useQuery } from "react-query";
import { useLocation, useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { getAdminAccessTokenSelector } from "../../../atom";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";
import {
  courseApi,
  getCourseTicketsApi,
  getCurriculumApi,
} from "../../../api/courseApi";
import { activeCourseApi } from "../../../api/adminApi";
import CourseIntro from "../../course/CourseIntro";
import styled from "styled-components";
import CourseImg from "../../course/CourseImg";
import SimpleCurriculumCheck from "../../creator/SimpleCurriculumCheck";
import { useState } from "react";

const DividerBox = styled.div`
  margin: 0 auto;
  width: 90%;
`;

export default function DetailCourseInfo() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const { courseId } = useParams();
  const navigate = useNavigate();
  const { state } = useLocation();

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

  const curriculum = useQuery(
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

  const allowCourse = () => {
    setIsLoading(true);
    activeCourseApi(accessToken, courseId)
      .then(() => {
        alert("승인 완료");
        navigate(-1);
      })
      .catch((err) => {
        setIsLoading(false);
        alert(err);
      });
  };
  const [isLoading, setIsLoading] = useState(false);
  return (
    <>
      <DashboardTitleTab title="강좌 상세 보기" />
      {state?.state === "enroll" && !isLoading && (
        <button onClick={allowCourse}>강좌 승인하기</button>
      )}

      <br />
      <br />
      <div>원 가격: {paymentInfo?.data?.data?.costPrice} </div>
      <div>할인 가격: {paymentInfo?.data?.data?.discountedPrice} </div>
      <div>강의 수강 기간: {paymentInfo?.data?.data?.coursePeriod} </div>
      <br />
      <br />
      <DividerBox>
        <CourseIntro courseId={courseId} />
        <br />
        <CourseImg images={info?.data?.descriptionImages} />
        <br />
        <br />
        <div>커리큘럼</div>
        <br />
        <br />
        <SimpleCurriculumCheck
          curriculumList={curriculum?.data?.data?.chapters}
        />

        <br />
        <br />

        {/* <CoursePayment
          ticketInfo={paymentInfo?.data?.data}
          title={info?.data?.title}
          teacher={info?.data?.instructor}
          courseId={courseId}
        /> */}
      </DividerBox>
    </>
  );
}
