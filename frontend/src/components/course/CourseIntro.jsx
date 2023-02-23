import ReactPlayer from "react-player";
import styled from "styled-components";
import { queryClient } from "../../index";
import { STATIC_URL } from "../../static";

const IntroduceWrapper = styled.div`
  width: 100%;
  height: 400px;
  background-color: var(--color-course-intro);
  border-radius: var(--size-border-radius);
  overflow: hidden;
  margin-bottom: 25px;
  box-sizing: border-box;
  padding: 20px 30px;
  box-shadow: 0 0 10px rgb(0 0 0 / 16%), 0 3px 10px rgb(0 0 0 / 16%);
`;

const IntroTitle = styled.h1`
  font-weight: var(--weight-point);
  font-size: var(--size-mypage-title);
`;

const MoreInfoBox = styled.div`
  width: 100%;
  height: 90%;
  display: flex;
  justify-content: space-between;
`;

const IntroPlayerTab = styled.div`
  margin-top: 20px;
  width: 60%;
  height: 96%;
`;

const DetailInfoBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-direction: column;
  align-items: flex-end;
  text-align: end;
`;

const TeacherTab = styled.div`
  width: 100%;
  font-size: 20px;
  font-weight: var(--weight-middle);
`;
const DescriptionTab = styled.div`
  font-weight: var(--weight-thin);
  height: 215px;
  color: var(--color-gray);
  font-size: 0.9rem;
`;

export default function CourseIntro({ courseId }) {
  const courseInfo = queryClient.getQueryData(["courseDetailInfo", courseId]);

  return (
    <IntroduceWrapper>
      <IntroTitle>{courseInfo?.title}</IntroTitle>
      <MoreInfoBox>
        <IntroPlayerTab>
          {courseInfo && (
            <ReactPlayer
              width="100%"
              height="100%"
              url={STATIC_URL + courseInfo?.sample?.videoInfo?.filePath}
              controls
            />
          )}
        </IntroPlayerTab>
        <DetailInfoBox>
          <TeacherTab>강사: {courseInfo?.instructor}</TeacherTab>
          <br />
          <DescriptionTab>{courseInfo?.description}</DescriptionTab>
        </DetailInfoBox>
      </MoreInfoBox>
    </IntroduceWrapper>
  );
}
