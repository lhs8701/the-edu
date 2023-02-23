import { Link } from "react-router-dom";
import styled from "styled-components";
import { PROCESS_MAIN_URL } from "../static";

const DaboomFooter = styled.footer`
  width: 1100px;
  margin: 0 auto;
  font-size: var(--size-footer);
  margin-top: 15vh;
  border-top: 1px solid var(--color-box-gray);
  padding: 30px 0px;
  box-sizing: border-box;
`;

const FooterBox = styled.div`
  font-weight: var(--weight-thin);
  color: var(--color-gray);
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
  margin-bottom: 15px;
  line-height: 15px;
`;

const InfoFooterBox = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(15%, auto));
  font-size: 1rem;
  font-weight: var(--weight-middle);
  width: 50%;
  flex: 1;
`;

const InfoLink = styled(Link)`
  text-decoration: none;
  color: var(--color-text);
  &:hover {
    color: var(--color-gray);
  }
`;

export default function Footer() {
  return (
    <DaboomFooter>
      <InfoFooterBox>
        <InfoLink to={PROCESS_MAIN_URL.TERM}>이용 약관</InfoLink>
        <InfoLink to={PROCESS_MAIN_URL.PRIVACY}>개인정보 처리 방침</InfoLink>
      </InfoFooterBox>
      <br />
      <FooterBox>
        <p>
          상호명&nbsp;:&nbsp;(주)조은캠프&nbsp;ㅣ&nbsp;대표이사&nbsp;:&nbsp;이유락&nbsp;ㅣ&nbsp;서울특별시
          금천구 가산디지털1로 75-24 아이에스비즈타워 14층
          <br />
          사업자번호&nbsp;:&nbsp;504-81-62507&nbsp;ㅣ&nbsp;본사&nbsp;:&nbsp;02-556-3650&nbsp;ㅣ&nbsp;기술지원&nbsp;:&nbsp;070-7019-1144&nbsp;ㅣ&nbsp;팩스&nbsp;:&nbsp;050-7711-2200&nbsp;ㅣ&nbsp;
          <br />
          원격평생교육시설13호&nbsp;ㅣ&nbsp;출판사&nbsp;:&nbsp;345-2005-00013&nbsp;ㅣ&nbsp;통신판매업&nbsp;:&nbsp;2005-대구북구-00158&nbsp;ㅣ&nbsp;정보보호책임자&nbsp;:&nbsp;김창연&nbsp;&nbsp;
          <br />
          COPYRIGHT 2005 (주)조은캠프 ALL RIGHTS RESERVED.
          <br />
          <a>관리자에게 메일보내기</a>
        </p>
      </FooterBox>
    </DaboomFooter>
  );
}
