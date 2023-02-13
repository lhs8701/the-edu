import styled from "styled-components";

const DaboomFooter = styled.footer`
  width: 1100px;
  margin: 0 auto;
  font-weight: var(--weight-thin);
  color: var(--color-gray);
  font-size: var(--size-footer);
  margin-top: 15vh;
  border-top: 1px solid var(--color-box-gray);
  padding: 40px 0px 30px 0px;
  box-sizing: border-box;
`;

const FooterBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
  margin-bottom: 15px;
  line-height: 15px;
`;

export default function Footer() {
  return (
    <DaboomFooter>
      <FooterBox>
        <p>
          상호명&nbsp;:&nbsp;(주)조은캠프&nbsp;ㅣ&nbsp;대표이사&nbsp;:&nbsp;이유락&nbsp;ㅣ&nbsp;대구광역시
          북구 대학로80 경북대학교 IT융합산업빌딩 11층
          <br />
          사업자번호&nbsp;:&nbsp;504-81-62507&nbsp;ㅣ&nbsp;본사&nbsp;:&nbsp;053-954-0030&nbsp;ㅣ&nbsp;기술지원&nbsp;:&nbsp;070-7019-1144&nbsp;ㅣ&nbsp;팩스&nbsp;:&nbsp;050-7711-2200&nbsp;ㅣ&nbsp;
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
