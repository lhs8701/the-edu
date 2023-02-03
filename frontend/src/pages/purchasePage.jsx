import { useLayoutEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { enrollApi } from "../api/courseApi";
import {
  getAccessTokenSelector,
  getLoginState,
  getMemberIdSelector,
} from "../atom";
import { UnderBar } from "../components/course/ChatComponents";
import { PROCESS_ACCOUNT_URL } from "../static";
import { TabTitle } from "../style/CommonCss";

import {
  DiscountTab,
  ONOFFTab,
  OwnPriceTab,
  PaymentBox,
  PriceBox,
  PriceUnderBar,
  PrimaryCostTab,
  PurchaseBtn,
  Select,
} from "../style/CourseCss";

const DividerBox = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 50px;
`;
const MyPageWrapper = styled.div`
  width: 100%;
`;

const InfoSection = styled.div`
  width: 60%;
  height: 100%;
`;

const CardSection = styled.div`
  width: 40%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const InfoBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-direction: column;
  flex: 1;
  padding: 10px 20px;
  box-sizing: border-box;
`;

const InfoTab = styled.div`
  width: 100%;
  display: flex;
  align-items: baseline;
  justify-content: space-between;
`;

const PaymentCard = styled(PaymentBox)`
  width: 80%;
  height: 450px;
  padding: 10px 20px;
  box-sizing: border-box;
  position: relative;
  top: 0;
`;
const PaymentP = styled.p`
  font-size: 1.3rem;
  font-weight: var(--weight-middle);
`;

const PaymentBtn = styled(PurchaseBtn)`
  width: 80%;
  margin-top: 25px;
`;

export default function PurchasePage() {
  const { courseId } = useParams();
  const navigate = useNavigate();
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const memberId = useRecoilValue(getMemberIdSelector);
  const loginState = useRecoilValue(getLoginState);

  const enrollCourse = () => {
    enrollApi(courseId, accessToken)
      .then((res) => {
        console.log(res);
        alert("구매가 완료 되었습니다.");
        navigate("/");
      })
      .catch((err) => {
        alert("이미 구입한 강좌입니다.");
        navigate(0);
      });
  };

  const PurchaseThing = () => {
    return (
      <>
        <InfoBox>
          <InfoTab>
            <p>제목</p>
            <p>강사</p>
            <p>원</p>
          </InfoTab>
          <br />
          <br />
          <InfoTab>
            <p>날짜</p>
            <ONOFFTab>오프/온</ONOFFTab>
          </InfoTab>
        </InfoBox>
        <UnderBar />
      </>
    );
  };

  const PurchaseCard = () => {
    return (
      <PaymentCard>
        <PaymentP>쿠폰 할인</PaymentP>
        <Select name="purchaseOption">
          {/* {purchaseOption.purchaseOptionInfo.map((opt, idx) => (
            <option>{opt.optionName}</option>
          ))} */}
        </Select>
        <PaymentP>포인트 할인</PaymentP>
        <PriceBox>
          <PrimaryCostTab>쿠폰 할인</PrimaryCostTab>
          <PrimaryCostTab></PrimaryCostTab>
          <DiscountTab>포인트 할인</DiscountTab>
          <PrimaryCostTab></PrimaryCostTab>
          <PriceUnderBar />
          <OwnPriceTab>총 할인 금액</OwnPriceTab>
          <PrimaryCostTab></PrimaryCostTab>
          <PriceUnderBar />
          <OwnPriceTab>총 결제 금액</OwnPriceTab>
        </PriceBox>
      </PaymentCard>
    );
  };

  useLayoutEffect(() => {
    if (!loginState) {
      window.location.replace(PROCESS_ACCOUNT_URL.LOGIN);
      alert("확인되지 않은 접근입니다.");
    }
  }, []);

  return (
    <MyPageWrapper>
      <TabTitle>결제</TabTitle>
      <DividerBox>
        <InfoSection>
          <PurchaseThing />
        </InfoSection>
        <CardSection>
          <PurchaseCard />
          <PaymentBtn onClick={enrollCourse}>결제</PaymentBtn>
        </CardSection>
      </DividerBox>
    </MyPageWrapper>
  );
}
