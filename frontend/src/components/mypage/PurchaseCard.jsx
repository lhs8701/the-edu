import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { refundApi } from "../../api/orderApi";
import { getAccessTokenSelector } from "../../atom";
import { STATIC_URL } from "../../static";
import { Card, CardTitle, CardInfoBox } from "../../style/MypageComponentsCss";

const HistoryCard = styled(Card)`
  height: 180px;
  padding-left: 0;
  cursor: auto;
`;

const HistoryInfoBox = styled(CardInfoBox)`
  width: 100%;
  flex-direction: column;
  justify-content: space-between;
  height: 85%;
  margin-left: 20px;
`;

const CourseInfoTab = styled.div`
  display: flex;
  align-content: center;
  justify-content: space-between;
`;

const CourseTeacher = styled.p`
  font-weight: var(--weight-thin);
  color: var(--color-gray);
`;

const CourseInfo = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const PurchaseId = styled(CourseTeacher)``;

const Date = styled(PurchaseId)`
  text-align: start;
`;
const Money = styled(PurchaseId)`
  text-align: end;
  font-weight: var(--weight-point);
  color: var(--color-text);
`;

const Img = styled.img`
  width: 220px;
  height: 100%;
`;

const CancelBtn = styled.button`
  background-color: var(--color-primary);
  border: none;
  padding: 5px 10px;
  border-radius: var(--size-border-radius);
  font-size: 0.9rem;
  transition: 0.1s;
  &:hover {
    box-shadow: 0 0.5em 0.5em -0.4em var(--hover);
    transform: translateY(-0.25em);
  }
`;

export default function PurchaseCard({ purchase }) {
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const refund = () => {
    if (window.confirm(`${purchase.orderName}을 환불하시겠습니까?`)) {
      refundApi(purchase.orderId, accessToken)
        .then(() => {
          alert("환불이 완료되었습니다.");
        })
        .catch((err) => {
          alert("환불이 불가합니다.");
        });
    }
  };

  return (
    <HistoryCard>
      <Img src={STATIC_URL + purchase.image.mediumFilePath} />
      <HistoryInfoBox>
        <CourseInfoTab>
          <CourseInfo>
            <CardTitle>{purchase.orderName}</CardTitle>
            {purchase.orderStatus === "DONE" ? (
              Number(purchase.amount) === 0 ? null : (
                <CancelBtn onClick={refund}>주문 취소</CancelBtn>
              )
            ) : null}
          </CourseInfo>
        </CourseInfoTab>
        <PurchaseId>주문 번호 : {purchase.orderId}</PurchaseId>
        <PurchaseId>
          결제 방식 : {purchase.payType === "CARD" && "카드 결제"}
          {purchase.payType === "MOBILE" && "휴대전화"}
          {purchase.payType === "EMPTY" && "결제 없음"}
        </PurchaseId>
        <PurchaseId>
          주문 상태 :{" "}
          {purchase.orderStatus === "DONE" ? "결제완료" : "환불 완료"}
        </PurchaseId>
        <CourseInfoTab>
          <Date>{purchase.orderTime.slice(0, 10)} 일에 주문</Date>
          <Money>{purchase.amount}원</Money>
        </CourseInfoTab>
      </HistoryInfoBox>
    </HistoryCard>
  );
}
