import { useState } from "react";
import { useQuery } from "react-query";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getAllCouponListApi } from "../../../api/adminApi";
import { getAdminAccessTokenSelector } from "../../../atom";
import { ADMIN_BAR_LIST } from "../../../static";
import { CouponTable } from "../../BasicTable";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

export default function CouponList() {
  const navigate = useNavigate();
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const userListTableCells = [
    { name: "쿠폰 ID", id: 0 },
    { name: "쿠폰 이름", id: 1 },
    { name: "할인 방식", id: 2 },
    { name: "할인률", id: 3 },
    { name: "종료 날짜", id: 4 },
    { name: "만료 여부", id: 5 },
    { name: "쿠폰 코드 조회", id: 6 },
  ];

  const { data, isSuccess } = useQuery(
    ["admin-allCoupons"],
    () => {
      return getAllCouponListApi(accessToken);
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
    <div>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[5].list[1].name} />
      {isSuccess && (
        <CouponTable
          cells={userListTableCells}
          rows={data?.data}
          navigate={navigate}
        />
      )}
    </div>
  );
}
