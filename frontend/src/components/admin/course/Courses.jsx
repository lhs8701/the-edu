import { FormControl, InputLabel, NativeSelect } from "@mui/material";
import { Box } from "@mui/system";
import { useInView } from "framer-motion";
import { useEffect, useMemo, useRef } from "react";
import { useInfiniteQuery, useQuery } from "react-query";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import {
  getReadyCourseListApi,
  lockCoursesApi,
  stopCoursesApi,
} from "../../../api/adminApi";
import { getAllCoursesApi } from "../../../api/courseApi";
import { getAdminAccessTokenSelector } from "../../../atom";
import { ADMIN_BAR_LIST, CREATOR_BAR_LIST } from "../../../static";
import { BottomDiv } from "../../../style/CommonCss";
import { AdminCoursesTable, AdminReadyCoursesTable } from "../../BasicTable";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

export default function Courses() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const navigate = useNavigate();
  const PAGE_SIZE = 50;
  const bottomRef = useRef(null);
  const isInView = useInView(bottomRef);
  const courseList = useInfiniteQuery(
    ["adminAllCoursesList"],
    ({ pageParam = 0 }) => {
      return getAllCoursesApi(pageParam, PAGE_SIZE);
    },
    {
      retry: 1,
      retryDelay: 5000,
      onSuccess: () => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
      getNextPageParam: (lastPage, allPages) => {
        if (Number(lastPage.totalPage) === 0) {
          return undefined;
        }
        if (lastPage.totalPage === lastPage.page + 1) {
          return undefined;
        }
        return lastPage.page + 1;
      },
    }
  );

  const courses = useMemo(
    () =>
      courseList?.data?.pages
        .flatMap((page) => page.list)
        .sort((a, b) => {
          return a.courseId - b.courseId;
        }),
    [courseList?.data?.pages]
  );

  const courseListTableCells = [
    { name: "강좌 ID", id: 0 },
    { name: "강좌 제목", id: 1 },
    { name: "강사", id: 2 },
    { name: "카테고리", id: 3 },
    { name: "상세 보기", id: 4 },
    { name: "정지 하기", id: 5 },
  ];

  const stopCourse = (courseId) => {
    if (window.confirm(`${courseId}번 강좌를 정지하시겠습니까?`)) {
      lockCoursesApi(accessToken, courseId)
        .then(() => {
          alert("강의를 정지하였습니다.");
        })
        .catch((err) => {
          alert(err);
        });
    }
  };

  useEffect(() => {
    if (isInView && courseList.hasNextPage && courseList.isSuccess) {
      courseList.fetchNextPage();
    }
  }, [isInView, courseList.data]);

  return (
    <>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[2].list[1].name} />
      <AdminCoursesTable
        cells={courseListTableCells}
        rows={courses}
        navigate={navigate}
        stopFun={stopCourse}
      />
      <BottomDiv ref={bottomRef} />
    </>
  );
}
