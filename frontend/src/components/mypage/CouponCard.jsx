import { motion } from "framer-motion";
import styled from "styled-components";

const Card = styled(motion.div)`
  width: 100%;
  height: 120px;
  background-color: var(--color-background);
  border-radius: var(--size-border-radius);
  overflow: hidden;
  border: 1.7px solid var(--color-text);
  box-shadow: 3px 3px 4px rgba(0, 0, 0, 0.25);
  margin-bottom: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
`;

const CouponInfoBox = styled.div`
  width: 90%;
  height: 100px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const CouponTitle = styled.h1`
  font-size: 21px;
  font-weight: var(--weight-point);
`;

const CouponDiscountTab = styled.div`
  height: 28px;
  width: 100%;
  display: flex;
  align-items: flex-end;
  justify-content: flex-end;
`;

const DiscountP = styled.p`
  font-weight: var(--weight-middle);
  font-size: 21px;
  color: var(--color-gray);
`;
const DiscountDateP = styled(DiscountP)`
  font-size: 18px;
`;
const DiscountRate = styled(DiscountP)`
  font-weight: var(--weight-point);
  color: var(--color-text);
  font-size: 28px;
  width: 135px;
  text-align: end;
`;

export default function CouponCard({ coupon }) {
  return (
    <Card whileHover={{ y: "-5px" }} whileTap={{ y: "0px" }}>
      <CouponInfoBox>
        <CouponTitle>{coupon?.title}</CouponTitle>
        <CouponDiscountTab>
          <DiscountP>원래 가격에서 </DiscountP>
          <DiscountRate>
            {coupon?.discountValue}
            {coupon?.discountRate ? "%" : "원"}
          </DiscountRate>
          <DiscountP>&nbsp; &nbsp;할인</DiscountP>
        </CouponDiscountTab>
        <DiscountDateP>{coupon?.expireDate}</DiscountDateP>
      </CouponInfoBox>
    </Card>
  );
}
