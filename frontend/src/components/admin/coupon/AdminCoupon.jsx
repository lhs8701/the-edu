import { Button, Grid, MenuItem } from "@mui/material";
import { useState } from "react";
import { useQuery } from "react-query";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { generateCouponApi, getAllCouponListApi } from "../../../api/adminApi";
import { getAdminAccessTokenSelector } from "../../../atom";
import { ADMIN_BAR_LIST } from "../../../static";
import { CouponTable } from "../../BasicTable";
import { CssTextField } from "../../creator/uploadCourse/Outline";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

export default function AdminCoupon() {
  const [name, setName] = useState();
  const [minumumAmount, setMinimumAmount] = useState();
  const [policy, setPolicy] = useState();
  const [discount, setDiscount] = useState();
  const [date, setDate] = useState(new Date());
  const navigate = useNavigate();
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);

  const couponTableCells = [
    { name: "쿠폰 ID", id: 0 },
    { name: "쿠폰 이름", id: 1 },
    { name: "할인 방식", id: 2 },
    { name: "할인률", id: 3 },
    { name: "종료 날짜", id: 4 },
    { name: "만료 여부", id: 5 },
    { name: "쿠폰 발급", id: 6 },
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

  const uploadGenerate = () => {
    generateCouponApi(accessToken, {
      name: name,
      amount: minumumAmount,
      policy: policy,
      discount: discount,
      endDate: date,
    })
      .then(() => {
        alert("쿠폰이 발급되었습니다.");
      })
      .catch((err) => {
        alert(err);
      });
  };

  return (
    <>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[5].list[0].name} />
      <Grid container spacing={2}>
        <Grid item xs={5}>
          <CssTextField
            fullWidth
            type="text"
            id="title"
            name="title"
            label="쿠폰 이름"
            size="small"
            value={name}
            onChange={(e) => {
              setName(e.target.value);
            }}
          />
        </Grid>
        <Grid item xs={3}>
          <CssTextField
            fullWidth
            type="number"
            id="detail"
            name="detail"
            label="최소 사용 가능 금액"
            size="small"
            value={minumumAmount}
            onChange={(e) => {
              setMinimumAmount(e.target.value);
            }}
          />
        </Grid>
        <Grid item xs={3}>
          <CssTextField
            size="small"
            fullWidth
            value={policy}
            onChange={(e) => {
              setPolicy(e.target.value);
            }}
            select
            label="쿠폰 정책"
            variant="outlined"
            id="cate1"
          >
            {/* <MenuItem value={"FIX"} key={0}>
              고정 가격 할인
            </MenuItem> */}
            <MenuItem value={"RATE"} key={1}>
              % 할인
            </MenuItem>
          </CssTextField>
        </Grid>
        <Grid item xs={2}>
          <CssTextField
            fullWidth
            type="number"
            id="price"
            name="price"
            label="할인 가격"
            size="small"
            value={discount}
            inputProps={{ min: 4, max: 10 }}
            onChange={(e) => {
              if (e.target.value >= 90) {
                alert("90퍼 이상 쿠폰 발급이 허용되지 않습니다.");
                return;
              }
              setDiscount(e.target.value);
            }}
          />
        </Grid>
        <Grid item xs={4}>
          <CssTextField
            fullWidth
            type="date"
            id="price"
            name="price"
            label="쿠폰 만료 날짜"
            size="small"
            value={date}
            onChange={(e) => {
              setDate(e.target.value);
            }}
          />
        </Grid>
        <Grid item xs={11} sx={{ display: "flex", justifyContent: "flex-end" }}>
          <Button
            sx={{
              backgroundColor: "var(--color-primary)",
              "&:hover": {
                backgroundColor: "#b1b1b1",
              },
            }}
            onClick={uploadGenerate}
            variant="contained"
            component="label"
          >
            Upload
          </Button>
        </Grid>
      </Grid>
      <br />
      <br />
      <div>
        <DashboardTitleTab title="전체 쿠폰 관리" />
        {isSuccess && (
          <CouponTable
            cells={couponTableCells}
            rows={data?.data}
            navigate={navigate}
          />
        )}
      </div>
    </>
  );
}
