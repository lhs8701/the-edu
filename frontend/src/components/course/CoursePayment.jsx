import { useNavigate } from "react-router";
import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart as alreadyHeart } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-regular-svg-icons";
import { PROCESS_MAIN_URL } from "../../static";
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
} from "../../style/CourseCss";
import { useEffect, useLayoutEffect, useState } from "react";
import { courseWishApi, courseWishCheckApi } from "../../api/courseApi";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
import { useRecoilValue } from "recoil";

const IconBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;
const Title = styled.h1`
  font-size: 18px;
  font-weight: var(--weight-point);
`;
const Teacher = styled.h2`
  width: 100%;
  font-size: 15px;
`;

const Icon = styled(FontAwesomeIcon)`
  width: 1.5rem;
  height: 1.5rem;
  color: var(--color-primary);
  cursor: pointer;
  &:hover {
    scale: 1.2;
  }
  &:active {
    scale: 0.8;
  }
`;

export default function CoursePayment({
  title,
  teacher,
  purchaseOption,
  courseId,
}) {
  const navigate = useNavigate();
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const memberId = useRecoilValue(getMemberIdSelector);
  const [isWishState, setIsWishState] = useState();
  const [isWishPushState, setIsWishPushState] = useState();

  useLayoutEffect(() => {
    courseWishCheckApi(memberId, courseId, accessToken)
      .then(({ data }) => {
        if (data) {
          setIsWishState(true);
        } else {
          setIsWishState(false);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  useEffect(() => {
    if (isWishState) {
      setIsWishPushState(true);
    } else {
      setIsWishPushState(false);
    }
  }, [isWishState]);

  const pushWish = () => {
    setIsWishPushState((prev) => !prev);
    courseWishApi(memberId, courseId, accessToken);
  };
  console.log(isWishPushState, isWishState);
  return (
    <PaymentBox>
      <IconBox>
        <ONOFFTab>온/오프</ONOFFTab>
        {isWishPushState ? (
          <Icon onClick={pushWish} icon={alreadyHeart} />
        ) : (
          <Icon onClick={pushWish} icon={faHeart} />
        )}
      </IconBox>
      <Title>{title}</Title>
      <Teacher>{teacher} 강사</Teacher>
      <Select name="purchaseOption">
        {purchaseOption.purchaseOptionInfo.map((opt, idx) => (
          <option>{opt.optionName}</option>
        ))}
      </Select>
      <br />
      <PriceBox>
        <PrimaryCostTab>원 가격</PrimaryCostTab>
        <PrimaryCostTab></PrimaryCostTab>
        <DiscountTab>할인 가격</DiscountTab>
        <PrimaryCostTab></PrimaryCostTab>
        <PriceUnderBar />
        <OwnPriceTab>결제 가격</OwnPriceTab>
        <PrimaryCostTab></PrimaryCostTab>
      </PriceBox>

      <PurchaseBtn
        onClick={() => {
          navigate(PROCESS_MAIN_URL.PURCHASE);
        }}
      >
        결제
      </PurchaseBtn>
    </PaymentBox>
  );
}
