import { motion } from "framer-motion";
import styled from "styled-components";

export const Title = styled.h1`
  font-size: var(--size-category-title);
  font-weight: var(--weight-point);
`;

export const Select = styled.select`
  width: 100%;
  height: 25px;
`;
export const PriceBox = styled.div`
  width: 100%;
`;
export const ONOFFTab = styled.div`
  width: 65px;
  background-color: var(--color-primary);
  border-radius: 8px;
  font-weight: var(--weight-point);
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const PrimaryCostTab = styled.h1`
  color: var(--color-gray);
  font-weight: var(--weight-thin);
  font-size: 18px;
  margin: 10px 0px;
`;

export const DiscountTab = styled(PrimaryCostTab)`
  color: var(--color-red);
`;

export const OwnPriceTab = styled(PrimaryCostTab)`
  color: var(--color-text);
`;

export const PriceUnderBar = styled.div`
  width: 100%;
  height: 1.6px;
  background-color: var(--color-gray);
`;

export const PurchaseBtn = styled.button`
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

export const PaymentBox = styled.div`
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
  margin: 0 auto;
`;
