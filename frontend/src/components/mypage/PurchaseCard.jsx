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

const Img = styled.img`
  width: 150px;
  height: 100%;
`;

export default function PurchaseCard({ purchase }) {
  return (
    <Card>
      <Img src="https://influencer-phinf.pstatic.net/MjAyMTAzMDhfMTcg/MDAxNjE1MTc5Mzc1MDIw.pX2vbhOo0R7oMThLzoyfasywzZCH8tLiOt_0xZ81rAAg.ZCGj0dvLR-Td_tkuRdWCf4mVQLTWe9p98DiXboFjM_0g.JPEG/%EC%9D%B8%ED%94%84%EB%9F%B0_%ED%95%9C%EA%B8%80_%EB%A1%9C%EA%B3%A0.jpg" />
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
