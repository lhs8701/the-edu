import ReactPlayer from "react-player";
import styled from "styled-components";

const IntroduceWrapper = styled.div`
  width: 100%;
  height: 400px;
  background-color: var(--color-course-intro);
  border-radius: var(--size-border-radius);
  overflow: hidden;
  margin-bottom: 25px;
  box-sizing: border-box;
  padding: 20px 30px;
`;

const IntroTitle = styled.h1`
  font-weight: var(--weight-point);
  font-size: var(--size-mypage-title);
`;

const IntroPlayerTab = styled.div`
  margin-top: 20px;
  width: 60%;
  height: 88%;
  /* background-color: var(--color-background); */
`;

export default function CourseIntro({ courseinfo }) {
  return (
    <IntroduceWrapper>
      <IntroTitle>제목</IntroTitle>
      <IntroPlayerTab>
        <ReactPlayer
          width="100%"
          height="100%"
          url="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
          controls
        />
      </IntroPlayerTab>
    </IntroduceWrapper>
  );
}
