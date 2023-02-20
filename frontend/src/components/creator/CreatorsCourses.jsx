import { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getUploadedCoursesApi } from "../../api/creatorApi";
import { getAccessTokenSelector } from "../../atom";
import { CREATOR_BAR_LIST } from "../../static";
import { MyUploadCoursesTable } from "../BasicTable";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";

export default function CreatorsCourses() {
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const navigate = useNavigate();
  const stanbyCreatorsList = [
    { name: "강좌 ID", id: 0 },
    { name: "강좌 제목", id: 1 },
    { name: "카테고리", id: 2 },
    { name: "승인 여부", id: 3 },
    { name: "상세 설정", id: 4 },
  ];

  const { data, isSuccess } = useQuery(
    ["creator-allCourses"],
    () => {
      return getUploadedCoursesApi(accessToken);
    },
    {
      enabled: !!accessToken,
      onSuccess: ({ data }) => {},
      onError: (res) => {
        alert("크레에이터가 아닙니다.");
      },
    }
  );

  return (
    <div>
      <DashboardTitleTab title={CREATOR_BAR_LIST.list[2].list[0].name} />
      {isSuccess && (
        <MyUploadCoursesTable
          cells={stanbyCreatorsList}
          rows={data?.data}
          navigate={navigate}
        />
      )}
    </div>
  );
}
