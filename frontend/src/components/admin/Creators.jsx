import { useEffect, useState } from "react";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import { ADMIN_BAR_LIST } from "../../static";
import { useRecoilValue } from "recoil";
import { getAdminAccessTokenSelector } from "../../atom";
import { useQuery } from "react-query";
import { getAllCreatorsApi } from "../../api/adminApi";
import { AdminCreatorsTable } from "../BasicTable";

export default function Creators() {
  const [creatorList, setCreatorList] = useState();
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);

  const stanbyCreatorsList = [
    { name: "크리에이터 ID", id: 0 },
    { name: "이름", id: 1 },
    { name: "이메일", id: 2 },
    { name: "전화번호", id: 3 },
  ];
  const { data, isSuccess } = useQuery(
    ["admin-allCreators"],
    () => {
      return getAllCreatorsApi(accessToken);
    },
    {
      enabled: !!accessToken,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const makeStanbyList = () => {
    if (isSuccess) {
      setCreatorList(
        [...data?.data].sort((a, b) => {
          return a.memberId - b.id;
        })
      );
    }
  };

  useEffect(makeStanbyList, [data?.data]);

  return (
    <div>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[1].list[0].name} />
      {creatorList && (
        <AdminCreatorsTable cells={stanbyCreatorsList} rows={creatorList} />
      )}
    </div>
  );
}
