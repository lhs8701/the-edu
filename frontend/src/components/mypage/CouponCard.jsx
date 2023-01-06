import styled from "styled-components";
import { Card, CardTitle, CardInfoBox } from "../../style/MypageComponentsCss";

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
      <CardInfoBox>
        <CardTitle>{coupon?.title}</CardTitle>
        <CouponDiscountTab>
          <DiscountP>원래 가격에서 </DiscountP>
          <DiscountRate>
            {coupon?.discountValue}
            {coupon?.discountRate ? "%" : "원"}
          </DiscountRate>
          <DiscountP>&nbsp; &nbsp;할인</DiscountP>
        </CouponDiscountTab>
        <DiscountDateP>{coupon?.expireDate}</DiscountDateP>
      </CardInfoBox>
    </Card>
  );
}
