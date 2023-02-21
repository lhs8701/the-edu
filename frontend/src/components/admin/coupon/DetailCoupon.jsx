import { Button, Grid } from "@mui/material";
import { useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { generateCouponCodeApi } from "../../../api/adminApi";
import { getAdminAccessTokenSelector } from "../../../atom";
import { CssTextField } from "../../creator/uploadCourse/Outline";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";
import UserImportCoupon from "../event/UserImportCoupon";
import Users from "../Users";

export default function DetailCoupon() {
  const { state } = useLocation();
  const { couponId } = useParams();
  const [count, setCount] = useState(0);
  const [isCode, setIsCode] = useState(true);
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

  return (
    <div>
      <div>
        <Button
          onClick={() => {
            setIsCode(true);
          }}
        >
          코드 생성 및 코드로 발급
        </Button>
        <Button
          onClick={() => {
            setIsCode(false);
          }}
        >
          유저에게 직접 발급
        </Button>
      </div>
      <br />
      {isCode ? (
        <>
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
          <br />
          <Grid container xs={12} spacing={2}>
            {state?.map((code) => {
              return (
                <Grid item xs={2} key={code}>
                  {code}
                </Grid>
              );
            })}
          </Grid>
        </>
      ) : (
        <UserImportCoupon />
      )}
    </div>
  );
}
