import styled from "styled-components";

const PaymentBox = styled.div`
  width: 28%;
  height: 400px;
  background-color: var(--color-background);
  border-radius: var(--size-border-radius);
  border: 2px solid #d0cfcf;
  box-sizing: border-box;
  padding: 10px;
  position: sticky;
  top: 200px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;
  text-align: end;
`;

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
const PurchaseBtn = styled.button`
  border: none;
  width: 100%;
  height: 30px;
  border-radius: 5px;
  display: block;
  margin: 0 auto;
  text-align: center;
  background-color: var(--color-primary);
  font-weight: var(--weight-point);
  font-size: 18px;
  &:hover {
    color: var(--color-background);
  }
`;
const PriceBox = styled.div`
  width: 100%;
  height: 150px;
`;
const ONOFFTab = styled.div`
  width: 65px;
  background-color: var(--color-primary);
  border-radius: 8px;
  font-weight: var(--weight-point);
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const Select = styled.select`
  width: 100%;
  height: 25px;
`;

const PrimaryCostTab = styled.h1`
  color: var(--color-gray);
  font-weight: var(--weight-middle);
  font-size: 18px;
  margin: 10px 0px;
`;

const DiscountTab = styled(PrimaryCostTab)`
  color: var(--color-red);
`;

const OwnPriceTab = styled(PrimaryCostTab)`
  color: var(--color-text);
`;

const PriceUnderBar = styled.div`
  width: 100%;
  height: 2px;
  background-color: var(--color-gray);
`;

export default function CoursePayment({ title, teacher, purchaseOption }) {
  return (
    <PaymentBox>
      <IconBox>
        <ONOFFTab>온/오프</ONOFFTab>
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

      <PurchaseBtn>결제</PurchaseBtn>
    </PaymentBox>
  );
}
