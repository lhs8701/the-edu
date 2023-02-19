import { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import { getUploadedCoursesApi } from "../../api/creatorApi";
import { getAccessTokenSelector } from "../../atom";
import { CREATOR_BAR_LIST } from "../../static";
import { MyUploadCoursesTable } from "../BasicTable";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";

export default function CreatorsCourses() {
  const [coursesList, setCoursesList] = useState();
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const stanbyCreatorsList = [
    { name: "강좌 ID", id: 0 },
    { name: "강좌 제목", id: 1 },
    { name: "카테고리", id: 2 },
    { name: "승인 여부", id: 3 },
  ];

  const { data, isSuccess } = useQuery(
    ["admin-allCreators"],
    () => {
      return getUploadedCoursesApi(accessToken);
    },
    {
      enabled: !!accessToken,
      onSuccess: ({ data }) => {
        setCoursesList(data);
      },
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  // useEffect(makeStanbyList, [data?.data]);

  return (
    <div>
      <DashboardTitleTab title={CREATOR_BAR_LIST.list[2].list[0].name} />
      {isSuccess && (
        <MyUploadCoursesTable cells={stanbyCreatorsList} rows={coursesList} />
      )}
    </div>
  );

  return;
}
