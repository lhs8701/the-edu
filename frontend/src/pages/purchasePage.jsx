import {
  FormControl,
  FormControlLabel,
  FormLabel,
  Radio,
  RadioGroup,
} from "@mui/material";
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
const PurchaseMethodCheckLabel = styled.label`
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  cursor: pointer;
  padding: 0 10px;
  border: 2px solid lightgrey;
  transition: all 0.3s ease;
  /* height: 20px;
  width: 20px; */
  background: #d9d9d9;
  border-radius: 50%;
  position: relative;
`;

const SmallTitle = styled(FormLabel)`
  font-size: 1.3rem;
  font-weight: var(--weight-middle);
  color: var(--color-text);
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

const MethodsBox = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  row-gap: 10px;
  width: 100%;
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

  const purchaseCourse = () => {
    if (purchaseMethod) {
      purchase(purchaseInfo, purchaseMethod);
    } else {
      alert("결제 방식을 선택해 주세요!");
    }
  };

  const PurchaseMethods = () => {
    return (
      <>
        <FormControl sx={{ width: "100%" }}>
          <SmallTitle>결제 방식</SmallTitle>
          <br />
          <RadioGroup
            defaultValue={purchaseMethod}
            name="radio-buttons-group"
            onChange={(e) => {
              setPurchaseMethod(e.target.value);
            }}
          >
            <MethodsBox>
              {PURCHASE_METHOD.map((method, idx) => {
                return (
                  <FormControlLabel
                    key={idx}
                    value={method}
                    label={method}
                    control={<Radio />}
                    sx={{
                      display: "flex",
                      justifyContent: "flexStart",
                    }}
                  />
                );
              })}
            </MethodsBox>
          </RadioGroup>
        </FormControl>
      </>
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
        <SmallTitle>쿠폰 할인</SmallTitle>
        <br />
        <Select name="purchaseOption">
          {/* {purchaseOption.purchaseOptionInfo.map((opt, idx) => (
            <option>{opt.optionName}</option>
          ))} */}
        </Select>
        <br />
        <SmallTitle>포인트 할인</SmallTitle>
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
        <PaymentBtn onClick={purchaseCourse}>결제</PaymentBtn>
      </DividerBox>
    </MyPageWrapper>
  );
}
