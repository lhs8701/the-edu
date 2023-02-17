import styled from "styled-components";
import { Card, CardTitle, CardInfoBox } from "../../style/MypageComponentsCss";

const CouponDiscountTab = styled.div`
  height: 28px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;

const DiscountP = styled.p`
  font-weight: var(--weight-middle);
  font-size: 1rem;
  color: var(--color-gray);
`;
const DiscountDateP = styled(DiscountP)`
  font-size: 18px;
`;
const DiscountRate = styled(DiscountP)`
  font-weight: var(--weight-point);
  color: var(--color-primary);
  font-size: 1.1rem;
  text-align: end;
`;

export default function CouponCard({ coupon }) {
  console.log(coupon);
  return (
    <Card whileHover={{ y: "-5px" }} whileTap={{ y: "0px" }}>
      <CardInfoBox>
        <CardTitle>{coupon?.name}</CardTitle>
        <CouponDiscountTab>
          <DiscountRate>
            {coupon?.discount}
            {coupon?.discountPolicy === "RATE" ? "%" : "원"}
          </DiscountRate>
          <DiscountP>&nbsp; &nbsp;할인</DiscountP>
        </CouponDiscountTab>
        <DiscountDateP>{coupon?.endDate}</DiscountDateP>
      </CardInfoBox>
    </Card>
  );
}
