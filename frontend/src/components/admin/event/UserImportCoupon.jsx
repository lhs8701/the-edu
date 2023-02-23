import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import { getAllUsersApi, importCouponApi } from "../../../api/adminApi";
import { ADMIN_BAR_LIST } from "../../../static";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";
import { getAdminAccessTokenSelector } from "../../../atom";
import { useEffect, useState } from "react";
import { AdminUserTable, CouponUserTable } from "../../BasicTable";
import { Button } from "@mui/material";
import { useParams } from "react-router";

export default function UserImportCoupon() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const { couponId } = useParams();
  const [userList, setUserList] = useState();
  const userListTableCells = [
    { name: "시스템 ID", id: 0 },
    { name: "계정", id: 1 },
    { name: "닉네임", id: 2 },
    { name: "전화번호", id: 3 },
    { name: "로그인 타입", id: 4 },
    { name: "가입 날짜", id: 5 },
    { name: "쿠폰 발급", id: 6 },
  ];
  const { data, isSuccess } = useQuery(
    ["admin-allUsers"],
    () => {
      return getAllUsersApi(accessToken);
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
  const importCoupon = (memberId) => {
    importCouponApi(accessToken, couponId, memberId)
      .then(() => {
        alert("쿠폰 발급");
      })
      .catch((err) => {
        if (err.response.data.code === -1002) {
          alert("이미 발급한 쿠폰입니다.");
        } else {
          alert("err");
        }
      });
  };

  useEffect(makeUserList, [data?.data]);

  return (
    <div>
      <DashboardTitleTab title="지정해서 쿠폰 발급" />
      <Button
        sx={{ backgroundColor: "var(--color-background)", color: "black" }}
        variant="contained"
      >
        전체에게 쿠폰 지급
      </Button>
      <br />
      <br />
      {userList && (
        <CouponUserTable
          cells={userListTableCells}
          rows={userList}
          importCouponFun={importCoupon}
        />
      )}
    </div>
  );
}
