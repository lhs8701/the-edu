import { useQuery } from "react-query";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getReadyCourseListApi } from "../../../api/adminApi";
import { getAdminAccessTokenSelector } from "../../../atom";
import { ADMIN_BAR_LIST } from "../../../static";
import { AdminReadyCoursesTable } from "../../BasicTable";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

export default function EnrollCourses() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const navigate = useNavigate();

  const courseListTableCells = [
    { name: "강좌 ID", id: 0 },
    { name: "강좌 제목", id: 1 },
    { name: "강사", id: 2 },
    { name: "카테고리", id: 3 },
    { name: "자세히 보기", id: 4 },
  ];

  const { data, isSuccess } = useQuery(
    ["admin-allCourses"],
    () => {
      return getReadyCourseListApi(accessToken);
    },
    {
      enabled: !!accessToken,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );
  return (
    <>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[2].list[0].name} />
      {isSuccess && (
        <AdminReadyCoursesTable
          cells={courseListTableCells}
          rows={data?.data}
          navigate={navigate}
        />
      )}
    </>
  );
}
