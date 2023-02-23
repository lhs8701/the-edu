import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import { getLockedMembersApi, unLockedMembersApi } from "../../api/adminApi";
import { ADMIN_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import { getAdminAccessTokenSelector } from "../../atom";
import { useEffect, useState } from "react";
import { AdminUserTable } from "../BasicTable";

export default function LockUsers() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const [userList, setUserList] = useState();
  const userListTableCells = [
    { name: "시스템 ID", id: 0 },
    { name: "계정", id: 1 },
    { name: "닉네임", id: 2 },
    { name: "전화번호", id: 3 },
    { name: "로그인 타입", id: 4 },
    { name: "크리에이터", id: 5 },
    { name: "가입 날짜", id: 6 },
    { name: "정지 해제", id: 7 },
  ];
  const { data, isSuccess } = useQuery(
    ["admin-allUsers"],
    () => {
      return getLockedMembersApi(accessToken);
    },
    {
      enabled: !!accessToken,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const userFilter = (userList) => {
    const filterList = userList.filter((user) => {
      return {
        account: user.account,
        creator: user.creator,
        id: user.id,
        loginType: user.loginType,
        nickname: user.nickname,
        mobile: user.mobile,
        joinedDate: user.joinedDate,
      };
    });

    return filterList;
  };
  const makeUserList = () => {
    if (isSuccess) {
      setUserList(
        userFilter([...data?.data]).sort((a, b) => {
          return a.id - b.id;
        })
      );
    }
  };

  const lockMember = (memberId) => {
    if (window.confirm(`${memberId}번 유저를 정지 해제하시겠습니까?`)) {
      unLockedMembersApi(accessToken, memberId)
        .then(() => {
          alert("정지 해제하였습니다.");
        })
        .catch((err) => {
          alert(err);
        });
    }
  };

  useEffect(makeUserList, [data?.data]);
  return (
    <div>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[0].list[1].name} />
      {userList && (
        <AdminUserTable
          cells={userListTableCells}
          rows={userList}
          fun={lockMember}
          isLockPage={true}
        />
      )}
    </div>
  );
}
