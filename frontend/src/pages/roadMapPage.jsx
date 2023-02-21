import { useNavigate } from "react-router";
import styled from "styled-components";
import { MoveBtn, TabTitle, Wrapper } from "../style/CommonCss";

const PrepareDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
`;

export default function RoadMapPage() {
  const navigate = useNavigate();
  return (
    <Wrapper>
      <TabTitle>로드맵</TabTitle>
      <br />
      <PrepareDiv>준비 중인 서비스입니다.</PrepareDiv>
      <br />
      <br />
      <PrepareDiv>
        <MoveBtn
          onClick={() => {
            navigate("/");
          }}
        >
          홈으로 가기
        </MoveBtn>
      </PrepareDiv>
    </Wrapper>
  );
}
