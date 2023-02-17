import {
  FormControl,
  FormControlLabel,
  Radio,
  RadioGroup,
} from "@mui/material";
import { useEffect, useLayoutEffect, useState } from "react";
import { Outlet, useLocation, useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getItemReceiptApi } from "../api/orderApi";
import { getAccessTokenSelector, getLoginState } from "../atom";
import { UnderBar } from "../components/course/ChatComponents";
import { purchase } from "../purchase";
import {
  PROCESS_ACCOUNT_URL,
  PURCHASE_METHOD,
  STATIC_URL,
  PROCESS_MAIN_URL,
} from "../static";
import { AccountInput } from "../style/AccountComponentCss";
import { TabTitle } from "../style/CommonCss";

import {
  DiscountTab,
  ONOFFTab,
  OwnPriceTab,
  PriceBox,
  PriceUnderBar,
  PrimaryCostTab,
  PurchaseBtn,
  Select,
} from "../style/CourseCss";

const DividerBox = styled.div`
  width: 80%;
  margin: 0 auto;
  margin-bottom: 50px;
`;
const MyPageWrapper = styled.div`
  width: 100%;
`;

const CouponBox = styled.div`
  width: 30%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;

const InfoSection = styled.div``;
const PointInput = styled(AccountInput)`
  width: 30%;
`;
const InfoBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-direction: column;
  flex: 1;
  padding: 20px;
  box-sizing: border-box;
`;

const InfoTab = styled.div`
  width: 100%;
  display: flex;
  align-items: baseline;
  justify-content: space-between;
`;

const SmallTitle = styled.h1`
  font-size: 1.3rem;
  font-weight: var(--weight-middle);
  color: var(--color-text);
`;

const PaymentBtn = styled(PurchaseBtn)`
  width: 80%;
  margin-top: 25px;
`;
const Img = styled.img`
  width: 160px;
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

const FlexDiv = styled.div`
  display: flex;
  align-items: baseline;
  justify-content: space-between;
`;

const Div = styled.div`
  display: flex;
  align-items: baseline;
  justify-content: space-between;
`;

const PointP = styled.p`
  font-size: 0.8rem;
  font-weight: var(--weight-thin);
`;

export default function PurchasePage() {
  const ZERO = 0;
  const { courseId, itemId } = useParams();
  const { state } = useLocation();
  const [purchaseMethod, setPurchaseMethod] = useState();
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const loginState = useRecoilValue(getLoginState);
  const currentPath = useLocation().pathname;
  const [isPurchaseDone, setIsPurchaseDone] = useState(false);
  const [itemInfo, setItemInfo] = useState();
  const navigate = useNavigate();
  const [useCoupon, setUseCoupon] = useState({
    rate: true,
    value: -1,
    id: -1,
  });

  useEffect(() => {
    getItemReceiptApi(itemId, accessToken)
      .then(({ data }) => {
        setItemInfo(data);
      })
      .catch((err) => {
        if (err.response.data.code === -6010) {
          alert("본인인증이 필요한 서비스입니다.");
          navigate(
            "/" +
              PROCESS_MAIN_URL.MYPAGE.DEFAULT +
              PROCESS_MAIN_URL.MYPAGE.REVISE
          );
        }
      });
  }, []);

  const PurchaseThing = () => {
    return (
      <>
        <CourseInfoBox>
          <Img src={STATIC_URL + itemInfo?.productImage?.smallFilePath} />
          <InfoBox>
            <InfoTab
              style={{ cursor: "pointer" }}
              onClick={() => {
                navigate(PROCESS_MAIN_URL.COURSES + "/" + courseId);
              }}
            >
              <p>{itemInfo?.productName}</p>
              {isPurchaseDone ? <p></p> : <p>{state?.price}원</p>}
            </InfoTab>
            <br />
            <br />
            <InfoTab>
              <ONOFFTab>오프/온</ONOFFTab>
            </InfoTab>
          </InfoBox>
        </CourseInfoBox>
        <UnderBar />
      </>
    );
  };

  useLayoutEffect(() => {
    if (!loginState) {
      window.location.replace("/" + PROCESS_ACCOUNT_URL.LOGIN);
      alert("확인되지 않은 접근입니다.");
    }
  }, []);

  const couponChange = (e) => {
    if (Number(e.target.value) === -1) {
      setUseCoupon({
        rate: true,
        value: -1,
        id: e.target.value,
      });
      return;
    }
    if (
      itemInfo?.couponList[e.nativeEvent.target.options.selectedIndex - 1]
        .discountPolicy === "FIX"
    ) {
      setUseCoupon({
        rate: false,
        value:
          itemInfo?.couponList[e.nativeEvent.target.options.selectedIndex - 1]
            .discount,
        id: e.target.value,
      });
    } else {
      setUseCoupon({
        rate: true,
        value:
          itemInfo?.couponList[e.nativeEvent.target.options.selectedIndex - 1]
            .discount,
        id: e.target.value,
      });
    }
  };
  console.log(useCoupon);
  useEffect(() => {
    // url로 현재 위치 확인
    if (
      currentPath.slice(-5) === "fail" ||
      currentPath.slice(-7) === "success"
    ) {
      setIsPurchaseDone(true);
    } else {
      setIsPurchaseDone(false);
    }
  }, [currentPath]);

  const PurchaseForm = () => {
    const [usePoint, setUsePoint] = useState(0);

    const purchaseCourse = () => {
      let cost = 0;
      if (Number(useCoupon.value) === -1) {
        //안쓸때
        cost = state.price - usePoint;
      } else {
        if (useCoupon.rate) {
          cost =
            state.price - // 쿠폰쓰고 퍼센트 할인 쿠폰일때
            Number(((useCoupon.value * state.price) / 100).toFixed()) -
            usePoint;
        } else {
          // 쿠폰쓰고 정가 쿠폰일때
          cost = state.price - useCoupon.value - usePoint;
        }
      }
      console.log();
      if (Number(cost) <= 0) {
        cost = 1;
      }
      console.log(cost);
      if (purchaseMethod) {
        purchase(
          {
            courseId: courseId,
            itemId: itemId,
            couponId: useCoupon.id,
            point: usePoint,
            amount: cost,
            orderName: itemInfo.productName,
            customerName: itemInfo.consumer,
          },
          purchaseMethod
        );
      } else {
        alert("결제 방식을 선택해 주세요!");
      }
    };

    return (
      <div>
        <Div>
          <SmallTitle>
            포인트 할인
            <PointP>(현재 나의 포인트: {itemInfo?.point})</PointP>
          </SmallTitle>
          <PointInput
            type="text"
            value={usePoint}
            onChange={(e) => {
              if (e.target.value > 10000) {
                // 일반적으로 많은 입력
                alert("보유한 포인트보다 많은 입력은 허용되지 않습니다.");
                return;
              } else if (e.target.value > state.price) {
                // 구매가격보다 높은 가격 입력
                alert("구매가격보다 많은 입력은 허용되지 않습니다.");
                return;
              } else if (
                Number(e.target.value) +
                  Number(((useCoupon.value * state?.price) / 100).toFixed()) >=
                state.price
              ) {
                alert("포인트 + 쿠폰 할인 혜택이 원 가격보다 높습니다");

                setUsePoint(
                  state.price -
                    Number(((useCoupon.value * state?.price) / 100).toFixed())
                );
              }
              setUsePoint(e.target.value);
            }}
            min="0"
            max={itemInfo?.point}
            placeholder={itemInfo?.point + " 포인트를 사용하실 수 있어요."}
          />
          <SmallTitle>쿠폰 할인</SmallTitle>
          <CouponBox>
            {itemInfo?.couponList.length === 0 ? (
              <div>쿠폰이 없어요!</div>
            ) : (
              <Select
                name="couponOption"
                value={useCoupon.id}
                onChange={couponChange}
              >
                <option value={-1} key={0}>
                  선택 안함
                </option>
                {itemInfo?.couponList?.map((coupon) => (
                  <option value={coupon.id} key={coupon.id}>
                    {coupon?.name}
                  </option>
                ))}
              </Select>
            )}
          </CouponBox>
        </Div>
        <br />
        <br />
        <br />
        <PriceBox>
          <FlexDiv>
            <PrimaryCostTab>쿠폰 할인</PrimaryCostTab>
            <PrimaryCostTab>
              &nbsp;
              {Number(useCoupon.value) === -1
                ? ZERO
                : useCoupon.rate
                ? Number(((useCoupon.value * state?.price) / 100).toFixed())
                : useCoupon.value > state.price
                ? state.price
                : useCoupon.value}
              &nbsp;원
            </PrimaryCostTab>
          </FlexDiv>
          <FlexDiv>
            <DiscountTab>포인트 할인</DiscountTab>
            <PrimaryCostTab>{usePoint} 원</PrimaryCostTab>
          </FlexDiv>
          <FlexDiv>
            <OwnPriceTab>총 할인 금액</OwnPriceTab>
            <PrimaryCostTab>
              {Number(useCoupon.value) === -1 //no coupon
                ? usePoint
                : useCoupon.rate // used rate coupon
                ? Number(((useCoupon.value * state?.price) / 100).toFixed()) +
                    usePoint > // 원 가격보다 작을 경우와 큰 경우 구분
                  state.price
                  ? state.price
                  : Number(((useCoupon.value * state?.price) / 100).toFixed()) +
                    usePoint
                : useCoupon.value + usePoint > state.price
                ? state.price
                : useCoupon.value + usePoint}
              &nbsp;원
            </PrimaryCostTab>
          </FlexDiv>
          <br />
          <br />
          <FlexDiv>
            <SmallTitle>총 결제 금액</SmallTitle>
            <PrimaryCostTab>
              {Number(useCoupon.value) === -1 // 쿠폰 안쓸경우
                ? state?.price - usePoint
                : useCoupon.rate
                ? state?.price - // 쿠폰쓰고 퍼센트 할인 쿠폰일때
                  Number(((useCoupon.value * state.price) / 100).toFixed()) -
                  usePoint // 쿠폰쓰고 정가 쿠폰일때
                : state?.price - useCoupon.value - usePoint < 0
                ? 0
                : state?.price - useCoupon.value - usePoint}
              &nbsp;원
            </PrimaryCostTab>
          </FlexDiv>
        </PriceBox>
        <br />
        <br />
        <br />
        <PriceUnderBar />
        <br />
        <br />
        <br />
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
        <br />
        <br />
        <PaymentBtn onClick={purchaseCourse}>결제</PaymentBtn>
      </div>
    );
  };

  return (
    <MyPageWrapper>
      <TabTitle>결제</TabTitle>
      <DividerBox>
        <InfoSection>
          <PurchaseThing />
        </InfoSection>
        <br />
        <br />

        {isPurchaseDone ? <Outlet /> : <PurchaseForm />}
      </DividerBox>
    </MyPageWrapper>
  );
}
