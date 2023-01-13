import styled from "styled-components";
import { Card, CardTitle, CardInfoBox } from "../../style/MypageComponentsCss";

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
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 270px;
`;

const PurchaseId = styled(CourseTeacher)`
  width: 200px;
  text-align: end;
`;

const Date = styled(PurchaseId)`
  text-align: start;
`;
const Money = styled(PurchaseId)`
  text-align: end;
  font-weight: var(--weight-point);
  color: var(--color-text);
`;

export default function PurchaseCard({ purchase }) {
  return (
    <Card>
      <CardInfoBox>
        <CourseInfoTab>
          <CourseInfo>
            <CardTitle>{purchase.title}</CardTitle>
            <CourseTeacher>{purchase.teacher}</CourseTeacher>
          </CourseInfo>
          <PurchaseId>주문 번호 {purchase.purchaseId}</PurchaseId>
        </CourseInfoTab>
        <CourseInfoTab>
          <Date>
            {purchase.purchaseDate} ~ {purchase.expiredDate}
          </Date>
          <Money>{purchase.purchaseValue}원</Money>
        </CourseInfoTab>
      </CardInfoBox>
    </Card>
  );
}
