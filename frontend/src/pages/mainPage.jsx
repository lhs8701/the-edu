import styled from "styled-components";
import { SlideNotice } from "../components/SlideNotice";

const MainWrapper = styled.div`
  width: 100%;
  height: 100%;
`;

export default function MainPage() {
  return (
    <MainWrapper>
      <SlideNotice></SlideNotice>메인입니다
    </MainWrapper>
  );
}
