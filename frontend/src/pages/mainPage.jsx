import styled from "styled-components";
import ClassRankList from "../components/ClassRankList";
import { SlideNotice } from "../components/SlideNotice";

const MainWrapper = styled.div`
  width: 100%;
  height: 100%;
`;

export default function MainPage() {
  return (
    <MainWrapper>
      <SlideNotice />
      <ClassRankList />
    </MainWrapper>
  );
}
