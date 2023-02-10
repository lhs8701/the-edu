import styled from "styled-components";
import { dummyPurchaseList } from "../../dummy";
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
  return (
    <MyPageBox>
      <MyPageTitle>나의 구매 내역</MyPageTitle>
      <MyPageContentBox>
        <PurchaseWrapper>
          <CouponListBox>
            {dummyPurchaseList.map((purchase) => {
              return (
                <PurchaseCard key={purchase?.purchaseId} purchase={purchase} />
              );
            })}
          </CouponListBox>
        </PurchaseWrapper>
      </MyPageContentBox>
    </MyPageBox>
  );
}
