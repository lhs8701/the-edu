import { useNavigate } from "react-router";
import styled from "styled-components";
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

const IconBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;
const Title = styled.h1`
  font-size: 18px;
  font-weight: var(--weight-point);
`;
const Teacher = styled.h2`
  width: 100%;
  font-size: 15px;
`;

export default function CoursePayment({ title, teacher, purchaseOption }) {
  const navigate = useNavigate();
  return (
    <PaymentBox>
      <IconBox>
        <ONOFFTab>온/오프</ONOFFTab>
        하트
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
