import { CircularProgress } from "@mui/material";

import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";

import { postItemPurchaseApi } from "../../api/orderApi";
import { getAccessTokenSelector } from "../../atom";
import { PROCESS_MAIN_URL } from "../../static";
import { AlertP, CenterDiv, MoveBtn } from "../../style/CommonCss";

export default function SuccessPurchase() {
  const navigate = useNavigate();
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const orderId = new URL(window.location.href).searchParams.get("orderId");
  const paymentKey = new URL(window.location.href).searchParams.get(
    "paymentKey"
  );
  const amount = new URL(window.location.href).searchParams.get("amount");
  const { courseId, itemId, couponId, point } = useParams();
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    if (Number(couponId) === -1) {
      postItemPurchaseApi(itemId, accessToken, {
        couponId: null,
        point: Number(point),
        key: paymentKey,
        tossId: orderId,
        amount: amount,
      })
        .then(() => {
          setLoading(true);
        })
        .catch((err) => {
          console.log(err);
          if (err.response.status === 400) {
            alert("사용 불가한 쿠폰입니다.");
          } else {
            alert("결제에 오류가 있습니다.");
          }
          // navigate(-1);
        });
    } else {
      postItemPurchaseApi(itemId, accessToken, {
        couponId: Number(couponId),
        point: Number(point),
        key: paymentKey,
        tossId: orderId,
        amount: amount,
      })
        .then(() => {
          setLoading(true);
        })
        .catch((err) => {
          console.log(err);
          if (err.response.status === 400) {
            alert("사용 불가한 쿠폰입니다.");
          } else {
            alert("결제에 오류가 있습니다.");
          }
          // navigate(-1);
        });
    }
  }, []);
  console.log(
    "/" + PROCESS_MAIN_URL.COURSES + PROCESS_MAIN_URL.LOBBY + "/" + courseId
  );
  return (
    <CenterDiv>
      {loading ? (
        <div>
          <br />
          <br />
          <AlertP>결제가 완료되었습니다. 😃</AlertP>
          <br />
          <br />
          <CenterDiv>
            <MoveBtn
              onClick={() => {
                navigate(
                  PROCESS_MAIN_URL.COURSES +
                    "/" +
                    courseId +
                    PROCESS_MAIN_URL.LOBBY
                );
              }}
            >
              강좌 로비로 가기
            </MoveBtn>
          </CenterDiv>
        </div>
      ) : (
        <CircularProgress
          sx={{
            color: "var(--color-primary)",
          }}
        />
      )}
    </CenterDiv>
  );
}
