import { CircularProgress } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { postItemPurchaseApi } from "../../api/orderApi";
import { getAccessTokenSelector } from "../../atom";
import { PROCESS_MAIN_URL } from "../../static";

const ProgressDiv = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
`;
const BtnDiv = styled.div`
  display: flex;
`;
const SuccuessP = styled.p`
  font-size: 1.5rem;
  font-weight: var(--weight-middle);
`;

const MoveBtn = styled.button`
  width: 180px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: var(--color-background);
  font-size: 1rem;
`;

export default function SuccessPurchase() {
  const navigate = useNavigate();
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const orderId = new URL(window.location.href).searchParams.get("orderId");
  const paymentKey = new URL(window.location.href).searchParams.get(
    "paymentKey"
  );
  const amount = new URL(window.location.href).searchParams.get("amount");
  const { itemId, couponId, point } = useParams();
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    if (couponId === "undefined") {
      postItemPurchaseApi(itemId, accessToken, {
        couponId: null,
        point: Number(point),
        key: paymentKey,
        tossId: orderId,
        amount: amount,
      })
        .then(() => {
          alert("결제가 완료되었습니다.");
          setLoading(true);
        })
        .catch((err) => {
          console.log(err);
          alert("결제에 오류가 있습니다.");
          // navigate(-1);
        });
    } else {
      postItemPurchaseApi(itemId, accessToken, {
        couponId: Number(couponId),
        point: Number(point),
        key: paymentKey,
        tossId: orderId,
        amount: amount,
      });
    }
  }, []);

  return (
    <ProgressDiv>
      {loading ? (
        <div>
          <SuccuessP>결제가 완료되었습니다. 😃</SuccuessP>
          <br />
          <br />
          <ProgressDiv>
            <MoveBtn
              onClick={() => {
                navigate("/" + PROCESS_MAIN_URL.MYPAGE.DEFAULT);
              }}
            >
              나의 클래스로 가기
            </MoveBtn>
          </ProgressDiv>
        </div>
      ) : (
        <CircularProgress
          sx={{
            color: "var(--color-primary)",
          }}
        />
      )}
    </ProgressDiv>
  );
}
