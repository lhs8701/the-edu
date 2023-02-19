import { useQuery } from "react-query";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { getAdminAccessTokenSelector } from "../../../atom";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";
import {
  courseApi,
  getCourseTicketsApi,
  getCurriculumApi,
} from "../../../api/courseApi";
import { activeCourseApi } from "../../../api/adminApi";
import CourseInfo from "../../Player/CourseInfo";
import { STATIC_URL } from "../../../static";
import CourseIntro from "../../course/CourseIntro";
import CourseDetail from "../../course/CourseDetail";
import styled from "styled-components";
import CoursePayment from "../../course/CoursePayment";
import CourseCategory from "../../course/CourseCategory";
import CourseImg from "../../course/CourseImg";

const DividerBox = styled.div`
  margin: 0 auto;
  width: 90%;
`;

export default function DetailCourseInfo() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
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
    activeCourseApi(accessToken, courseId)
      .then(() => {
        alert("승인 완료");
      })
      .catch((err) => {
        alert(err);
      });
  };

  return (
    <>
      <DashboardTitleTab title="강좌 상세 보기" />
      <button onClick={allowCourse}>승인하기</button>

      <br />
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

        {curriculum?.data?.data?.chapters?.map((chapter) => {
          return (
            <>
              <div>{chapter?.title}</div>
              {chapter?.units?.map((unit) => {
                return (
                  <div
                    style={{ display: "flex", justifyContent: "space-between" }}
                  >
                    <div key={unit.unitId}>{unit.title}</div>
                    <button
                      onClick={() => {
                        navigate(`${unit.unitId}`);
                      }}
                    >
                      강좌 보기
                    </button>
                  </div>
                );
              })}
            </>
          );
        })}
        <br />
        <br />
        <CoursePayment
          ticketInfo={paymentInfo?.data?.data}
          title={info?.data?.title}
          teacher={info?.data?.instructor}
          courseId={courseId}
        />
      </DividerBox>
    </>
  );
}
