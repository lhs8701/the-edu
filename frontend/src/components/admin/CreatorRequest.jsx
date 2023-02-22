import { Typography } from "@mui/material";
import { useEffect, useLayoutEffect, useState } from "react";
import styled from "styled-components";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import { ADMIN_BAR_LIST } from "../../static";
import { useRecoilValue } from "recoil";
import { getAdminAccessTokenSelector } from "../../atom";
import { useQuery } from "react-query";
import { activateCreator, getAllCreatorStanbyApi } from "../../api/adminApi";
import { AdminCreatorStanbyTable } from "../BasicTable";

export default function CreatorRequest() {
  const [stanbyList, setStanbyList] = useState();
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);

  const stanbyCreatorsList = [
    { name: "승인 여부", id: 0 },
    { name: "이름", id: 1 },
    { name: "이메일", id: 2 },
    { name: "전화번호", id: 3 },
    { name: "개설 희망 과목", id: 4 },
    { name: "경력", id: 5 },
    { name: "승인하기", id: 6 },
  ];
  const { data, isSuccess } = useQuery(
    ["admin-stanbyCreators"],
    () => {
      return getAllCreatorStanbyApi(accessToken);
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
      setStanbyList(
        [...data?.data].sort((a, b) => {
          return a.id - b.id;
        })
      );
    }
  };

  const activeCreator = (creatorId) => {
    if (window.confirm(`해당 유저를 크리에이터로 승인하시겠습니까?`)) {
      activateCreator(accessToken, creatorId)
        .then(() => {
          alert("승인 완료");
        })
        .catch((err) => {
          alert(err);
        });
    }
  };

  useEffect(makeStanbyList, [data?.data]);
  console.log(stanbyList);
  return (
    <div>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[1].list[1].name} />
      {stanbyList && (
        <AdminCreatorStanbyTable
          cells={stanbyCreatorsList}
          rows={stanbyList}
          activeFun={activeCreator}
        />
      )}
    </div>
  );
}
