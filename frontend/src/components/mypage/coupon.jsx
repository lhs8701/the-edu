import { Tab } from "@mui/material";
import { useState } from "react";
import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import {
  getUnusedCouponApi,
  getUsedCouponApi,
  registCouponApi,
} from "../../api/couponApi";
import { getMyPointApi } from "../../api/myPageApi";

import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
import { dummyCouponList } from "../../dummy";
import { AccountInput, AccountSmallBtn } from "../../style/AccountComponentCss";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";
import {
  StatusDisplayUnderBar,
  StatusNavBar,
  StatusNavBox,
  StatusNavTab,
} from "../../style/StatusNavBarCss";
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
  color: var(--color-text);
`;

const CouponInput = styled(AccountInput)`
  width: 100%;
  background-color: var(--color-background);
`;
const PointDiv = styled.div`
  padding: 0px 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: var(--size-own-nav);
`;

const CouponBtn = styled(AccountSmallBtn)``;

const CouponListBox = styled.div`
  width: 100%;
  margin-top: 44px;
  margin-bottom: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`;

const AlertTitle = styled.h1`
  font-size: 1.1rem;
`;

export default function Coupon() {
  const [couponCode, setCouponCode] = useState("");
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const memberId = useRecoilValue(getMemberIdSelector);
  const [isTabStatus, setIsTabStatus] = useState(1);

  const myPoints = useQuery(
    ["myPoints", memberId],
    () => {
      return getMyPointApi(accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: (res) => {},
      onError: (err) => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const usedCouponList = useQuery(
    ["usedCoupons", memberId],
    () => {
      return getUsedCouponApi(accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: (res) => {},
      onError: (err) => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const unUsedCouponList = useQuery(
    ["unUsedCoupons", memberId],
    () => {
      return getUnusedCouponApi(accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: (res) => {},
      onError: (err) => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const registCoupon = () => {
    registCouponApi(accessToken, couponCode)
      .then(() => {
        alert("등록 완료");
        setCouponCode("");
      })
      .catch((err) => {
        if (err.response.status === 404) {
          alert("옳지 않은 쿠폰번호 입니다.");
        } else {
          alert(err);
        }
      });
  };

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
            <CouponRegisterTitle>나의 적립금 :</CouponRegisterTitle>
            &nbsp;&nbsp;&nbsp;
            <PointDiv>{myPoints?.data}</PointDiv>
          </div>
          <br />
          <br />
          <br />

          <CouponRegisterBox>
            <div style={{ width: "100%" }}>
              <CouponRegisterTitle>쿠폰 등록</CouponRegisterTitle>
              <CouponInput
                value={couponCode}
                onChange={(e) => {
                  setCouponCode(e.target.value);
                }}
                placeholder="쿠폰 번호를 입력하세요."
              />
            </div>
            <CouponBtn onClick={registCoupon}>등록</CouponBtn>
          </CouponRegisterBox>
          <br />
          <br />
          <br />
          <CouponRegisterTitle>쿠폰 목록</CouponRegisterTitle>
          <br />
          <StatusNavBar>
            <StatusNavBox>
              <StatusNavTab
                onClick={() => {
                  setIsTabStatus(1);
                }}
                ison={[isTabStatus, 1]}
              >
                사용하지 않은 쿠폰
                {isTabStatus === 1 && (
                  <StatusDisplayUnderBar layoutId="myCoupons" />
                )}
              </StatusNavTab>
              <StatusNavTab
                onClick={() => {
                  setIsTabStatus(2);
                }}
                ison={[isTabStatus, 2]}
              >
                사용한 쿠폰
                {isTabStatus === 2 && (
                  <StatusDisplayUnderBar layoutId="myCoupons" />
                )}
              </StatusNavTab>
            </StatusNavBox>
          </StatusNavBar>
          <CouponListBox>
            {isTabStatus === 1 ? (
              unUsedCouponList?.data?.data.length === 0 ? (
                <>
                  <br />
                  <AlertTitle>사용할 수 있는 쿠폰이 없어요.</AlertTitle>
                </>
              ) : (
                unUsedCouponList?.data?.data.map((coupon) => {
                  return <CouponCard key={coupon.id} coupon={coupon} />;
                })
              )
            ) : usedCouponList?.data?.data.length === 0 ? (
              <>
                <br />
                <AlertTitle>사용한 쿠폰이 없어요.</AlertTitle>
              </>
            ) : (
              usedCouponList?.data?.data.map((coupon) => {
                return <CouponCard key={coupon.id} coupon={coupon} />;
              })
            )}
          </CouponListBox>
        </CouponWrapper>
      </MyPageContentBox>
    </MyPageBox>
  );
}
