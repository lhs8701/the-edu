import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getOrdersApi, refundApi } from "../../api/orderApi";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
import { dummyPurchaseList } from "../../dummy";
import { AlertP, CenterDiv } from "../../style/CommonCss";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";
import PurchaseCard from "./PurchaseCard";

const PurchaseWrapper = styled.div`
  /* width: var(--size-revise-coupon-window); */
  width: 90%;
  height: 100%;
  margin: 0 auto;
`;

const CouponListBox = styled.div`
  width: 100%;
  margin-top: 44px;
`;

export default function PurchaseHistory() {
  const memberId = useRecoilValue(getMemberIdSelector);
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const { data, isSuccess } = useQuery(
    ["myPurchaseInfo", memberId],
    () => {
      return getOrdersApi(accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: ({ email, nickname, profileImage }) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  return (
    <MyPageBox>
      <MyPageTitle>나의 구매 내역</MyPageTitle>
      {isSuccess && (
        <MyPageContentBox>
          <PurchaseWrapper>
            <CouponListBox>
              {data?.data?.map((order, idx) => {
                return <PurchaseCard key={order.orderId} purchase={order} />;
              })}
            </CouponListBox>
          </PurchaseWrapper>
        </MyPageContentBox>
      )}
      {data?.data?.length === 0 && (
        <CenterDiv>
          <AlertP>구매가 없어요.</AlertP>
        </CenterDiv>
      )}
    </MyPageBox>
  );
}
