import { useEffect, useLayoutEffect, useState } from "react";
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
import { purchase } from "../purchase";
import { PROCESS_ACCOUNT_URL, PURCHASE_METHOD } from "../static";
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
  /* display: flex;
  justify-content: space-between; */
  width: 80%;
  margin: 0 auto;
  margin-bottom: 50px;
`;
const MyPageWrapper = styled.div`
  width: 100%;
`;

const InfoSection = styled.div``;

const CardSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const InfoBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-direction: column;
  flex: 1;

  padding: 25px;
  box-sizing: border-box;
`;

const InfoTab = styled.div`
  width: 100%;

  display: flex;
  align-items: baseline;
  justify-content: space-between;
`;

const PaymentCard = styled(PaymentBox)`
  padding: 10px 20px;
  box-sizing: border-box;
`;
const PaymentP = styled.p`
  font-size: 1.3rem;
  font-weight: var(--weight-middle);
`;

const PaymentBtn = styled(PurchaseBtn)`
  width: 80%;
  margin-top: 25px;
`;
const Img = styled.img`
  width: 150px;
  height: 100%;
`;

const CourseInfoBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 150px;
`;

const MethodInputBox = styled.div`
  width: 150px;
  height: 50px;
  border: 1px solid var(--color-box-gray);
  display: flex;
  align-items: center;
  justify-content: center;
`;
const MethodsBox = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  row-gap: 10px;
`;

export default function PurchasePage() {
  const { courseId } = useParams();
  const navigate = useNavigate();
  const [purchaseMethod, setPurchaseMethod] = useState();
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const loginState = useRecoilValue(getLoginState);
  const purchaseInfo = 1;

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

  const PurchaseMethods = () => {
    return (
      <MethodsBox>
        {PURCHASE_METHOD.map((method, idx) => {
          return (
            <label htmlFor={idx}>
              <MethodInputBox>
                <input
                  type="radio"
                  name="methods"
                  value={method}
                  id={idx}
                  onClick={(e) => {
                    setPurchaseMethod(e.target.value);
                  }}
                />{" "}
                {method}
              </MethodInputBox>
            </label>
          );
        })}
      </MethodsBox>
    );
  };

  const PurchaseThing = () => {
    return (
      <>
        <CourseInfoBox>
          <Img src="https://influencer-phinf.pstatic.net/MjAyMTAzMDhfMTcg/MDAxNjE1MTc5Mzc1MDIw.pX2vbhOo0R7oMThLzoyfasywzZCH8tLiOt_0xZ81rAAg.ZCGj0dvLR-Td_tkuRdWCf4mVQLTWe9p98DiXboFjM_0g.JPEG/%EC%9D%B8%ED%94%84%EB%9F%B0_%ED%95%9C%EA%B8%80_%EB%A1%9C%EA%B3%A0.jpg" />
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
        </CourseInfoBox>
        <UnderBar />
      </>
    );
  };

  const PurchaseInfo = () => {
    return (
      <div>
        <PaymentP>쿠폰 할인</PaymentP>
        <br />
        <Select name="purchaseOption">
          {/* {purchaseOption.purchaseOptionInfo.map((opt, idx) => (
            <option>{opt.optionName}</option>
          ))} */}
        </Select>
        <br />
        <PaymentP>포인트 할인</PaymentP>
        <PriceBox>
          <PrimaryCostTab>쿠폰 할인</PrimaryCostTab>
          <PrimaryCostTab></PrimaryCostTab>
          <DiscountTab>포인트 할인</DiscountTab>
          <PrimaryCostTab></PrimaryCostTab>
          <OwnPriceTab>총 할인 금액</OwnPriceTab>
          <PrimaryCostTab></PrimaryCostTab>
          <OwnPriceTab>총 결제 금액</OwnPriceTab>
        </PriceBox>
      </div>
    );
  };

  useLayoutEffect(() => {
    if (!loginState) {
      window.location.replace("/" + PROCESS_ACCOUNT_URL.LOGIN);
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
        <br />
        <br />
        <PurchaseInfo />
        <PurchaseMethods />
        <PaymentBtn
          onClick={() => {
            purchase(purchaseInfo, purchaseMethod);
          }}
        >
          결제
        </PaymentBtn>
      </DividerBox>
    </MyPageWrapper>
  );
}
