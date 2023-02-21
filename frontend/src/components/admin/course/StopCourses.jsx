import { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import {
  getReadyCourseListApi,
  getStopCoursesApi,
  unlockCoursesApi,
} from "../../../api/adminApi";
import { getAdminAccessTokenSelector } from "../../../atom";
import { ADMIN_BAR_LIST } from "../../../static";
import {
  AdminReadyCoursesTable,
  AdminStopCoursesTable,
} from "../../BasicTable";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";
export default function StopCourses() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const navigate = useNavigate();
  const [list, setList] = useState();
  const courseListTableCells = [
    { name: "강좌 ID", id: 0 },
    { name: "강좌 제목", id: 1 },
    { name: "강사", id: 2 },
    { name: "카테고리", id: 3 },
    { name: "자세히 보기", id: 4 },
    { name: "정지 해제", id: 5 },
  ];

  useEffect(() => {
    getStopCoursesApi(accessToken)
      .then(({ data }) => {
        setList(data);
      })
      .catch((err) => {
        alert(err);
      });
  }, []);
  const unlock = (courseId) => {
    unlockCoursesApi(accessToken, courseId)
      .then(() => {
        alert("강의를 햐제하였습니다.");
        window.location.reload();
      })
      .catch((err) => {
        alert(err);
      });
  };
  return (
    <>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[2].list[2].name} />

      <AdminStopCoursesTable
        cells={courseListTableCells}
        rows={list}
        navigate={navigate}
        unlockFun={unlock}
      />
    </>
  );
}
