import { Button } from "@mui/material";
import { useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { generateCouponCodeApi } from "../../../api/adminApi";
import { getAdminAccessTokenSelector } from "../../../atom";
import { CssTextField } from "../../creator/Outline";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

export default function DetailCoupon() {
  const { state } = useLocation();
  const { couponId } = useParams();
  const [count, setCount] = useState(0);
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const navigate = useNavigate();
  const makeCode = () => {
    generateCouponCodeApi(accessToken, couponId, count)
      .then((data) => {
        alert("생성 완료");
        navigate(-1);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  console.log(state);
  return (
    <div>
      <DashboardTitleTab title="쿠폰 코드 생성" />
      <CssTextField
        size="small"
        value={count}
        onChange={(e) => {
          setCount(e.target.value);
        }}
        label="쿠폰 발급 개수"
        variant="outlined"
        id="count"
        type="number"
      />

      <Button
        sx={{
          mx: 2,
          backgroundColor: "var(--color-primary)",
          "&:hover": {
            backgroundColor: "#b1b1b1",
          },
        }}
        onClick={makeCode}
        variant="contained"
        component="label"
      >
        쿠폰 생성
      </Button>
      <br />
      <br />
      <br />
      <DashboardTitleTab title="쿠폰 코드 조회" />
      <div>코드</div>
      <br />
      {state?.map((code) => {
        return <div key={code}>{code}</div>;
      })}
    </div>
  );
}
