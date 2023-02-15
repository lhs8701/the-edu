import styled from "styled-components";
import { dummyCouponList } from "../../dummy";
import { AccountInput, AccountSmallBtn } from "../../style/AccountComponentCss";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";
import CouponCard from "./CouponCard";

const CouponWrapper = styled.div`
  width: var(--size-revise-coupon-window);
  height: 100%;
  margin: 0 auto;
`;

const CouponRegisterBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  height: auto;
  align-items: flex-end;
  margin-bottom: 30px;
`;

const CouponRegisterTitle = styled.h1`
  font-size: var(--size-own-nav);
  color: var(--color-gray);
`;

const CouponInput = styled(AccountInput)`
  width: 100%;
  background-color: var(--color-background);
`;
const PointInput = styled(AccountInput)`
  width: 70%;
`;

const CouponBtn = styled(AccountSmallBtn)``;

const CouponListBox = styled.div`
  width: 100%;
  margin-top: 44px;
  margin-bottom: 150px;
`;

export default function Coupon() {
  return (
    <MyPageBox>
      <MyPageTitle>나의 쿠폰 및 적립금</MyPageTitle>
      <MyPageContentBox>
        <CouponWrapper>
          <div
            style={{
              display: "flex",
              alignItems: "baseline",
              justifyContent: "flex-start",
            }}
          >
            <CouponRegisterTitle>나의 적립금</CouponRegisterTitle>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <PointInput placeholder="적립금" disabled />
          </div>
          <br />
          <br />
          <br />

          <CouponRegisterBox>
            <div style={{ width: "100%" }}>
              <CouponRegisterTitle>쿠폰 등록</CouponRegisterTitle>
              <CouponInput placeholder="쿠폰 번호를 입력하세요." />
            </div>
            <CouponBtn>등록</CouponBtn>
          </CouponRegisterBox>
          <br />
          <br />
          <br />
          <CouponRegisterTitle>쿠폰 목록</CouponRegisterTitle>
          <CouponListBox>
            {dummyCouponList.map((coupon) => {
              return <CouponCard key={coupon.id} coupon={coupon} />;
            })}
          </CouponListBox>
        </CouponWrapper>
      </MyPageContentBox>
    </MyPageBox>
  );
}
