import styled from "styled-components";
import { faCheck } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Wrapper } from "../../style/CommonCss";
import { SideTitle, TitleBox } from "../../style/PlayerSideBarCss";

const PrepareBox = styled.div`
  width: 100%;
`;

const DetailBox = styled.div`
  width: 100%;
  margin-bottom: 10px;
`;

const DetailTab = styled.div`
  word-break: break-all;
  font-weight: var(--weight-point);
`;

const Detail = styled.div`
  width: 80%;
  margin-top: 20px;
  margin-left: 10px;
  padding: 10px 10px 10px 5px;
  border-radius: 5px;
  font-weight: lighter;
  color: #3c3c3c;
`;

const Icon = styled(FontAwesomeIcon)`
  font-weight: bolder;
  margin-right: 2px;
`;
const PrepareTab = styled.li`
  margin-top: 15px;
  margin-left: 15px;
  color: var(--color-gray);
`;

export default function CourseInfo() {
  return (
    <Wrapper>
      <TitleBox>
        <SideTitle>unitInfo.title</SideTitle>
      </TitleBox>
      <DetailBox>
        <DetailTab>강의 목표</DetailTab>
        <PrepareTab>unitDetail.objective</PrepareTab>

        <br />
        <br />
        <br />
        <DetailTab>세부 내용</DetailTab>
        <Detail>unitDetail.description</Detail>
      </DetailBox>
      <PrepareBox>
        <br />
        <br />
        <br />
        <DetailTab>
          <Icon icon={faCheck} />
          무엇이 필요할까요?
        </DetailTab>
        <Detail>Test....</Detail>
      </PrepareBox>
    </Wrapper>
  );
}
